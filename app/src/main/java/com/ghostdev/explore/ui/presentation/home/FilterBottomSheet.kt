package com.ghostdev.explore.ui.presentation.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghostdev.explore.R
import com.ghostdev.explore.ui.theme.black
import com.ghostdev.explore.ui.theme.orange
import com.ghostdev.explore.ui.theme.white
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    isDarkTheme: Boolean,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
    selectedContinents: Set<String>,
    selectedTimezones: Set<String>,
    onContinentSelected: (String, Boolean) -> Unit,
    onTimezoneSelected: (String, Boolean) -> Unit,
    onReset: () -> Unit,
    onApplyFilter: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var isContinentsExpanded by remember { mutableStateOf(false) }
    var isTimezonesExpanded by remember { mutableStateOf(false) }

    val continentArrowRotation by animateFloatAsState(
        targetValue = if (isContinentsExpanded) 180f else 0f,
        label = "continent arrow rotation"
    )

    val timezoneArrowRotation by animateFloatAsState(
        targetValue = if (isTimezonesExpanded) 180f else 0f,
        label = "timezone arrow rotation"
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Filter",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Continents Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isContinentsExpanded = !isContinentsExpanded },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Continent",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_down),
                            contentDescription = "expand continents",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(continentArrowRotation)
                        )
                    }

                    if (isContinentsExpanded) {
                        Spacer(modifier = Modifier.height(16.dp))
                        val continents = listOf(
                            "Africa", "Antarctica", "Asia", "Australia",
                            "Europe", "North America", "South America"
                        )

                        continents.forEach { continent ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .toggleable(
                                        value = selectedContinents.contains(continent),
                                        onValueChange = { selected ->
                                            onContinentSelected(continent, selected)
                                        }
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = continent,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Checkbox(
                                    checked = selectedContinents.contains(continent),
                                    onCheckedChange = { selected ->
                                        onContinentSelected(continent, selected)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Timezones Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isTimezonesExpanded = !isTimezonesExpanded },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Time Zone",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_down),
                            contentDescription = "expand timezones",
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(timezoneArrowRotation)
                        )
                    }

                    if (isTimezonesExpanded) {
                        Spacer(modifier = Modifier.height(16.dp))
                        val timezones = listOf(
                            "UTC−12:00", "UTC−11:00", "UTC−10:00", "UTC−09:30", "UTC−09:00",
                            "UTC−08:00", "UTC−07:00", "UTC−06:00", "UTC−05:00", "UTC−04:00",
                            "UTC−03:30", "UTC−03:00", "UTC−02:00", "UTC−01:00", "UTC±00:00",
                            "UTC+01:00", "UTC+02:00", "UTC+03:00", "UTC+03:30", "UTC+04:00",
                            "UTC+04:30", "UTC+05:00", "UTC+05:30", "UTC+05:45", "UTC+06:00",
                            "UTC+06:30", "UTC+07:00", "UTC+08:00", "UTC+08:45", "UTC+09:00",
                            "UTC+09:30", "UTC+10:00", "UTC+10:30", "UTC+11:00", "UTC+12:00",
                            "UTC+12:45", "UTC+13:00", "UTC+14:00"
                        )

                        timezones.forEach { timezone ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .toggleable(
                                        value = selectedTimezones.contains(timezone),
                                        onValueChange = { selected ->
                                            onTimezoneSelected(timezone, selected)
                                        }
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = timezone,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Checkbox(
                                    checked = selectedTimezones.contains(timezone),
                                    onCheckedChange = { selected ->
                                        onTimezoneSelected(timezone, selected)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = {
                            onReset()
                            isContinentsExpanded = true
                            isTimezonesExpanded = false
                        },
                        modifier = Modifier.weight(0.4f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = "Reset",
                            color = if (isDarkTheme) white else black
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                bottomSheetState.hide()
                            }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    onApplyFilter()
                                    onDismiss()
                                }
                            }
                        },
                        modifier = Modifier.weight(0.7f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = orange
                        )
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = "Show results",
                            color = white
                        )
                    }
                }
            }
        }
    }
}
