package com.ghostdev.explore.data.api

import com.ghostdev.explore.models.CountriesResponse
import com.ghostdev.explore.models.CountryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

interface CountriesApi {
    suspend fun getAllCountries(): CountriesResponse
    suspend fun getCountryByName(name: String): CountryResponse
}

class KtorCountriesApi(
    private val client: HttpClient
) : CountriesApi {

    companion object {
        private const val API_URL = "https://restfulcountries.com/api/v1/countries"
        private const val AUTH_TOKEN = "Bearer 2138|lIsUssMgBPBOAPXEDpbWk8kvmziXiGh1OhYWquSi"
    }

    override suspend fun getAllCountries(): CountriesResponse {
        return try {
            client.get(API_URL) {
                header("Authorization", AUTH_TOKEN)
                header("Accept", "application/json")
            }.body()
        } catch (e: Exception) {
            println("Error fetching countries: ${e.message}")
            CountriesResponse(emptyList())
        }
    }


    override suspend fun getCountryByName(name: String): CountryResponse {
        return try {
            client.get("$API_URL/$name") {
                header("Authorization", AUTH_TOKEN)
                header("Accept", "application/json")
            }.body()
        } catch (e: Exception) {
            println("Error fetching country '$name': ${e.message}")
            CountryResponse(null)
        }
    }
}
