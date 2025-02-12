package com.ghostdev.explore.ui.presentation.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
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
import com.ghostdev.explore.models.CountriesResponse
import com.ghostdev.explore.models.Country
import com.ghostdev.explore.navigation.NavDestinations
import com.ghostdev.explore.ui.presentation.BaseLogic
import com.ghostdev.explore.ui.presentation.base.BaseLoadingComposable
import com.ghostdev.explore.ui.theme.grey2
import com.ghostdev.explore.ui.theme.white
import kotlinx.coroutines.delay

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

    state.let { ready ->
        HomeScreen(
            countries = ready.value.countries,
            isLoading = ready.value.loading,
            innerPadding = innerPadding,
            isDarkTheme = isDarkTheme,
            toggleTheme = remember { toggleTheme },
            onCountryClick = remember { { country -> viewmodel.setSelectedCountry(country); controller.navigate(NavDestinations.Details.toString()) } },
            countrySearchText = countrySearchText,
            onCountrySearchTextChange = remember { { countrySearchText = it } },
            onSearch = remember { { viewmodel.getCountryByName(countrySearchText) } },
            onReset = remember { { countrySearchText = ""; viewmodel.getAllCountries() } }
        )
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
private fun HomeScreen(
    countries: CountriesResponse?,
    isLoading: Boolean = true,
    innerPadding: PaddingValues,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit,
    onCountryClick: (Country) -> Unit = {},
    countrySearchText: String = "",
    onCountrySearchTextChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onReset: () -> Unit = {}
) {

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
                modifier = Modifier
                    .size(80.dp),
                painter = painterResource(if (isDarkTheme) R.drawable.explore_dark else R.drawable.explore_light),
                contentDescription = "explore logo"
            )

            Spacer(modifier = Modifier.width(50.dp))

            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .clickable { toggleTheme() },
                painter = painterResource(if (isDarkTheme) R.drawable.dark_toggle else R.drawable.light_toggle),
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
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onSearch()
                        },
                    painter = painterResource(R.drawable.search),
                    contentDescription = "search country icon"
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch()
                }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .align(Alignment.End)
                .clickable {
                    onReset()
                },
            text = "Reset",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(12.dp))


        if (isLoading) {
            BaseLoadingComposable()
        } else {
            if (countries?.data?.isEmpty() == true) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "Error fetching data.\nTry again later",
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn {
                    items(
                        items = countries!!.data,
                        key = { it.iso2 ?: it.name ?: "" }
                    ) { country ->
                        val offsetX = remember { Animatable(-100f) }

                        LaunchedEffect(Unit) {
                            offsetX.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 500)
                            )
                        }

                        CountryItem(
                            countryImageUrl = country.href.flag ?: "",
                            countryName = country.name ?: "",
                            countryCapital = country.capital ?: "",
                            onClick = { onCountryClick(country) },
                            modifier = Modifier.offset(x = offsetX.value.dp)
                        )
                    }
                }
            }
        }
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
            .clickable {
                onClick()
            },
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