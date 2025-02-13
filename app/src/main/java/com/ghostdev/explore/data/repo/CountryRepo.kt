package com.ghostdev.explore.data.repo

import com.ghostdev.explore.models.Country

interface CountryRepo {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountryByName(name: String): List<Country>
}