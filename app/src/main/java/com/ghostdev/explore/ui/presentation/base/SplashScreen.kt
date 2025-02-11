package com.ghostdev.explore.ui.presentation.base

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ghostdev.explore.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onAnimationEnd: () -> Unit) {
    val fullText = "Explore."
    var visibleCharacters by remember { mutableIntStateOf(0) }

    val animatedCharacters by animateIntAsState(
        targetValue = visibleCharacters,
        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = 200, easing = LinearEasing
        ),
        finishedListener = {
            onAnimationEnd()
        }
    )

    LaunchedEffect(Unit) {
        for (i in 1..fullText.length) {
            visibleCharacters = i
            delay(65)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = fullText.take(animatedCharacters),
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(
                Font(
                    R.font.lily_script_one,
                    weight = FontWeight.Bold
                )
            )
        )
    }
}