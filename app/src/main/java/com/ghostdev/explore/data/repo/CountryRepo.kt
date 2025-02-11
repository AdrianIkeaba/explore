package com.ghostdev.explore.data.repo

import com.ghostdev.explore.models.CountriesResponse
import com.ghostdev.explore.models.CountryResponse

interface CountryRepo {
    suspend fun getAllCountries(): CountriesResponse
    suspend fun getCountryByName(name: String): CountryResponse
}