package com.ghostdev.explore.ui.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghostdev.explore.data.repo.CountryRepo
import com.ghostdev.explore.models.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BaseLogic : ViewModel(), KoinComponent {
    private val countryRepo: CountryRepo by inject()

    private val _appState = MutableStateFlow(AppState())
    val appState: StateFlow<AppState> = _appState

    init {
        getAllCountries()
    }

    fun getAllCountries() {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(loading = true)

            val countries = countryRepo.getAllCountries()
            _appState.value = _appState.value.copy(
                countries = countries,
                loading = false
            )
        }
    }

    fun getCountryByName(name: String) {
        viewModelScope.launch {
            _appState.value = _appState.value.copy(loading = true)

            val countries = countryRepo.getCountryByName(name)
            _appState.value = _appState.value.copy(
                loading = false,
                countries = countries
            )
        }
    }

    fun setSelectedCountry(country: Country) {
        _appState.value = _appState.value.copy(selectedCountryDetails = country)
    }
}


@Stable
data class AppState(
    val loading: Boolean = true,
    val countries: List<Country> = emptyList(),
    val selectedCountryDetails: Country? = null
)
