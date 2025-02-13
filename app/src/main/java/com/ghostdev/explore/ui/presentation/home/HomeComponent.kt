package com.ghostdev.explore.ui.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.ghostdev.explore.R
import com.ghostdev.explore.models.Country
import com.ghostdev.explore.navigation.NavDestinations
import com.ghostdev.explore.ui.presentation.BaseLogic
import com.ghostdev.explore.ui.presentation.base.BaseLoadingComposable
import com.ghostdev.explore.ui.theme.black
import com.ghostdev.explore.ui.theme.grey2
import com.ghostdev.explore.ui.theme.white

@Composable
fun HomeComponent(
    viewmodel: BaseLogic,
    controller: NavHostController,
    innerPadding: PaddingValues,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit
) {
    val state = viewmodel.appState.collectAsStateWithLifecycle()
    var countrySearchText by rememberSaveable { mutableStateOf("") }


    var selectedContinents by rememberSaveable { mutableStateOf(setOf<String>()) }
    var selectedTimezones by rememberSaveable { mutableStateOf(setOf<String>()) }

    HomeScreen(
        countries = state.value.countries,
        isLoading = state.value.loading,
        innerPadding = innerPadding,
        isDarkTheme = isDarkTheme,
        toggleTheme = toggleTheme,
        onCountryClick = { country ->
            viewmodel.setSelectedCountry(country)
            controller.navigate(NavDestinations.Details.toString())
        },
        countrySearchText = countrySearchText,
        onCountrySearchTextChange = { countrySearchText = it },
        onSearch = { viewmodel.getCountryByName(countrySearchText) },
        onReset = {
            countrySearchText = ""
            selectedContinents = emptySet()
            selectedTimezones = emptySet()
            viewmodel.getAllCountries()
        },
        selectedContinents = selectedContinents,
        selectedTimezones = selectedTimezones,
        onContinentSelected = { continent, selected ->
            selectedContinents = if (selected) selectedContinents + continent else selectedContinents - continent
        },
        onTimezoneSelected = { timezone, selected ->
            selectedTimezones = if (selected) selectedTimezones + timezone else selectedTimezones - timezone
        }
    )
}


@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun HomeScreen(
    countries: List<Country>?,
    isLoading: Boolean,
    innerPadding: PaddingValues,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit,
    onCountryClick: (Country) -> Unit,
    countrySearchText: String,
    onCountrySearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onReset: () -> Unit,
    selectedContinents: Set<String>,
    selectedTimezones: Set<String>,
    onContinentSelected: (String, Boolean) -> Unit,
    onTimezoneSelected: (String, Boolean) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    val filteredCountries = remember(countries, selectedContinents, selectedTimezones) {
        countries?.filter { country ->
            val matchesContinents = selectedContinents.isEmpty() || country.continents.any { it in selectedContinents }
            val matchesTimezones = selectedTimezones.isEmpty() || country.timezones.any { it in selectedTimezones }
            matchesContinents && matchesTimezones
        }
    }


    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(
                    if (isDarkTheme) R.drawable.explore_dark
                    else R.drawable.explore_light
                ),
                contentDescription = "explore logo"
            )

            Spacer(modifier = Modifier.width(50.dp))

            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .clickable { toggleTheme() },
                painter = painterResource(
                    if (isDarkTheme) R.drawable.dark_toggle
                    else R.drawable.light_toggle
                ),
                contentDescription = "theme toggle"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = countrySearchText,
            onValueChange = { onCountrySearchTextChange(it) },
            placeholder = {
                Text(
                    text = "Search Country",
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = if (isDarkTheme) white else grey2
                )
            },
            shape = RoundedCornerShape(8.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.search),
                    contentDescription = "search country icon"
                )
            },
            trailingIcon = {
                if (countrySearchText.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .clickable {
                                onCountrySearchTextChange("")
                            },
                        painter = painterResource(R.drawable.close),
                        contentDescription = "clear icon"
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch() })
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { showBottomSheet = true },
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    0.5.dp,
                    color = if (isDarkTheme) white else black
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.filter),
                    contentDescription = "filter icon",
                    tint = if (isDarkTheme) white else black
                )
                Text(
                    text = "Filter",
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkTheme) white else black
                )
            }

            Spacer(modifier = Modifier.width(40.dp))

            Text(
                modifier = Modifier.clickable {
                    onReset()
                },
                text = "Reset",
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (isLoading) {
            BaseLoadingComposable()
        } else if (filteredCountries == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Error fetching data.\nTry again later :(",
                    textAlign = TextAlign.Center
                )
            }
        } else if (filteredCountries.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "No data matches filter :(",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn {
                filteredCountries
                    .sortedBy { it.name.common }
                    .groupBy { it.name.common.first().uppercaseChar() }
                    .forEach { (letter, countryList) ->
                        item {
                            Text(
                                text = letter.toString(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 16.dp)
                            )
                        }

                        items(
                            items = countryList,
                            key = { it.name.common }
                        ) { country ->
                            val offsetX = remember { Animatable(-100f) }

                            LaunchedEffect(Unit) {
                                offsetX.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(durationMillis = 500)
                                )
                            }

                            CountryItem(
                                countryImageUrl = country.flags.png,
                                countryName = country.name.common,
                                countryCapital = country.capital.getOrElse(0) { "Unknown" },
                                onClick = { onCountryClick(country) },
                                modifier = Modifier.offset(x = offsetX.value.dp)
                            )
                        }
                    }
            }
        }

        FilterBottomSheet(
            isDarkTheme = isDarkTheme,
            showBottomSheet = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            selectedContinents = selectedContinents,
            selectedTimezones = selectedTimezones,
            onContinentSelected = onContinentSelected,
            onTimezoneSelected = onTimezoneSelected,
            onReset = onReset,
            onApplyFilter = { showBottomSheet = false }
        )
    }
}

@Composable
private fun CountryItem(
    modifier: Modifier = Modifier,
    countryImageUrl: String,
    countryName: String,
    countryCapital: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = countryImageUrl,
            placeholder = painterResource(R.drawable.loading_flag),
            contentDescription = "$countryName flag",
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = countryName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = countryCapital,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}