package com.ghostdev.explore.data.api

import com.ghostdev.explore.models.Country
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

interface CountriesApi {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountryByName(name: String): List<Country>
}

class KtorCountriesApi(
    private val client: HttpClient
) : CountriesApi {

    companion object {
        private const val API_URL = "https://restcountries.com/v3.1"
    }

    override suspend fun getAllCountries(): List<Country> {
        return try {
            client.get("$API_URL/all") {
                header("Accept", "application/json")
            }.body<List<Country>>()
        } catch (e: Exception) {
            println("Error fetching countries: ${e.message}")
            emptyList()
        }
    }


    override suspend fun getCountryByName(name: String): List<Country> {
        return try {
            client.get("$API_URL/name/$name") {
                header("Accept", "application/json")
            }.body()
        } catch (e: Exception) {
            println("Error fetching country '$name': ${e.message}")
            emptyList()
        }
    }
}
