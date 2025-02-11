package com.ghostdev.explore.models


import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountriesResponse(
    val data: List<Country>
)

@Serializable
data class CountryResponse(
    val data: Country?
)

@Stable
@Serializable
data class Country(
    val name: String?,
    @SerialName("full_name") val fullName: String?,
    val capital: String?,
    val iso2: String?,
    val iso3: String?,
    val covid19: Covid19Stats,
    @SerialName("current_president") val currentPresident: President?,
    val currency: String?,
    @SerialName("phone_code") val phoneCode: String?,
    val continent: String?,
    val description: String?,
    val size: String?,
    @SerialName("independence_date") val independenceDate: String?,
    val population: String?,
    val href: CountryLinks
)

@Serializable
data class Covid19Stats(
    @SerialName("total_case") val totalCases: String?,
    @SerialName("total_deaths") val totalDeaths: String?,
    @SerialName("last_updated") val lastUpdated: String?
)

@Serializable
data class President(
    val name: String?,
    val gender: String?,
    @SerialName("appointment_start_date") val appointmentStartDate: String?,
    @SerialName("appointment_end_date") val appointmentEndDate: String?,
    val href: PresidentLinks
)

@Serializable
data class PresidentLinks(
    val self: String?,
    val country: String?,
    val picture: String?
)

@Serializable
data class CountryLinks(
    val self: String?,
    val states: String?,
    val presidents: String?,
    val flag: String?
)
