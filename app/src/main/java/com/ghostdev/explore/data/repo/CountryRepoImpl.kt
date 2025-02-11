package com.ghostdev.explore.data.repo

import com.ghostdev.explore.data.api.CountriesApi
import com.ghostdev.explore.models.CountriesResponse
import com.ghostdev.explore.models.CountryResponse

class CountryRepoImpl(
    private val countriesApi: CountriesApi
): CountryRepo {
    override suspend fun getAllCountries(): CountriesResponse {
        return countriesApi.getAllCountries()
    }

    override suspend fun getCountryByName(name: String): CountryResponse {
        return countriesApi.getCountryByName(name)
    }
}