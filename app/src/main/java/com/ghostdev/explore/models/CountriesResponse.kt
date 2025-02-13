package com.ghostdev.explore.models

import android.os.Parcelable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Country(
    val name: CountryName = CountryName(),
    val population: Long = 0L,
    val continents: List<String> = emptyList(),
    val capital: List<String> = emptyList(),
    val languages: Map<String, String> = emptyMap(),
    val area: Double = 0.0,
    val currencies: Map<String, Currency> = emptyMap(),
    val timezones: List<String> = emptyList(),
    val idd: PhoneCode = PhoneCode(),
    val car: Car = Car(),
    val flags: Flag = Flag()
)

@Serializable
data class CountryName(
    val common: String = "",
    val official: String = "",
    val nativeName: Map<String, NativeName> = emptyMap()
)

@Serializable
data class NativeName(
    val official: String = "",
    val common: String = ""
)

@Serializable
data class Currency(
    val name: String = "",
    val symbol: String = ""
)

@Parcelize
@Serializable
data class PhoneCode(
    val root: String = "",
    val suffixes: List<String> = emptyList()
) : Parcelable

@Serializable
data class Car(
    val side: String = "unknown"
)

@Serializable
data class Flag(
    val png: String = "",
    val svg: String = "",
    val alt: String = "No description available"
)
