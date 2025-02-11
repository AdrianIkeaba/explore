package com.ghostdev.explore.ui.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ghostdev.explore.ui.presentation.base.BaseLoadingComposable
import org.koin.androidx.compose.koinViewModel

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
                text = selectedCountry?.name ?: "",
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            AsyncImage(
                model = selectedCountry?.href?.flag,
                placeholder = painterResource(R.drawable.loading_flag),
                contentDescription = "${selectedCountry?.name ?: ""} flag",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }

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
fun CountryInfoCard(
    country: Country?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        InfoText("Population", country?.population ?: "Not available")
        InfoText("Region", country?.continent ?: "Not available")
        InfoText("Capital", country?.capital ?: "Not available")
        InfoText("Size", country?.size ?: "Not available")

        Spacer(modifier = Modifier.height(8.dp))

        InfoText("Phone code", country?.phoneCode ?: "Not available")
        InfoText("Currency", country?.currency ?: "Not available")
        InfoText("Independence Date", country?.independenceDate ?: "Not available")
        InfoText("President", country?.currentPresident?.name ?: "Not available")

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
