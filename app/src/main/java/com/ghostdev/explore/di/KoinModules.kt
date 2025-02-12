package com.ghostdev.explore.di

import android.content.Context
import com.ghostdev.explore.data.api.CountriesApi
import com.ghostdev.explore.data.api.KtorCountriesApi
import com.ghostdev.explore.data.repo.CountryRepo
import com.ghostdev.explore.data.repo.CountryRepoImpl
import com.ghostdev.explore.ui.presentation.BaseLogic
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::KtorCountriesApi).bind(CountriesApi::class)
}

val provideHttpClientModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }, contentType = ContentType.Any)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
        }
    }
}

val repositoryModule  = module {
    singleOf(::CountryRepoImpl).bind(CountryRepo::class)
}

val viewmodelModule = module {
    factoryOf(::BaseLogic)
}

fun initKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(
            provideHttpClientModule,
            dataModule,
            repositoryModule,
            viewmodelModule
        )
    }
}