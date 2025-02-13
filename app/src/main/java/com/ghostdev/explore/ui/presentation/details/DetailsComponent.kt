package com.ghostdev.explore.ui.presentation.details

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.ghostdev.explore.R
import com.ghostdev.explore.models.Country
import com.ghostdev.explore.ui.presentation.BaseLogic
import com.ghostdev.explore.ui.theme.black
import com.ghostdev.explore.ui.theme.white
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailsComponent(
    viewmodel: BaseLogic,
    controller: NavHostController,
    innerPadding: PaddingValues
) {
    val state = viewmodel.appState.collectAsStateWithLifecycle()

    state.value.selectedCountryDetails.let { selectedCountry ->
        DetailsScreen(
            innerPadding = innerPadding,
            selectedCountry = selectedCountry,
            goBack = { controller.popBackStack() }
        )
    }
}

@Composable
private fun DetailsScreen(
    innerPadding: PaddingValues,
    selectedCountry: Country?,
    goBack: () -> Unit = {}
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
                    .clickable {
                        goBack()
                    },
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = "back arrow"
            )

            Text(
                text = selectedCountry?.name?.common ?: "",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier
                    .width(40.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        CountryImageCarousel(
            selectedCountry = selectedCountry
        )

        Spacer(
            modifier = Modifier
                .height(24.dp)
        )

        CountryInfoCard(
            country = selectedCountry
        )

    }
}

@Composable
fun CountryImageCarousel(selectedCountry: Country?) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val images = listOf(
        selectedCountry?.flags?.png ?: "",
        selectedCountry?.coatOfArms?.png ?: ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        AsyncImage(
            model = images[currentIndex],
            placeholder = painterResource(R.drawable.loading_flag),
            contentDescription = if (currentIndex == 0) {
                "${selectedCountry?.name?.common} flag"
            } else {
                "${selectedCountry?.name?.common} coat of arms"
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = if (currentIndex == 1) ContentScale.Fit else ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(black.copy(alpha = 0.3f))
                    .clickable { if (currentIndex > 0) currentIndex-- }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "Previous",
                    tint = white,
                    modifier = Modifier
                        .size(19.dp)
                        .align(Alignment.Center)
                        .offset(x = 2.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(black.copy(alpha = 0.3f))
                    .clickable { if (currentIndex < images.size - 1) currentIndex++ }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Next",
                    tint = white,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            images.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (currentIndex == index) white
                            else white.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}


@Composable
fun CountryInfoCard(
    country: Country?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InfoText("Population", (NumberFormat.getNumberInstance(Locale.US).format(country?.population) ?: "Not available").toString())
        InfoText("Region", country?.continents?.get(0) ?: "Not available")
        InfoText("Capital", country?.capital?.getOrElse(0) { "Not available" } ?: "Not available")
        InfoText("Size", (((NumberFormat.getNumberInstance(Locale.US).format(country?.area) + " km²")
            ?: "Not available")).toString())

        Spacer(modifier = Modifier.height(8.dp))

        InfoText("Phone code", (country?.idd?.root.toString() + country?.idd?.suffixes?.joinToString(", ")))
        InfoText("Currency", (country?.currencies?.values?.firstOrNull()?.name ?: "Not available").toString())

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold
        )
        Text(text = value)
    }
}
