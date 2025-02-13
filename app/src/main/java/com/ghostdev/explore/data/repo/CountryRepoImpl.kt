package com.ghostdev.explore.data.repo

import com.ghostdev.explore.data.api.CountriesApi
import com.ghostdev.explore.models.Country

class CountryRepoImpl(
    private val countriesApi: CountriesApi
): CountryRepo {
    override suspend fun getAllCountries(): List<Country> {
        return countriesApi.getAllCountries()
    }

    override suspend fun getCountryByName(name: String): List<Country> {
        return countriesApi.getCountryByName(name)
    }
}