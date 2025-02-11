package com.ghostdev.explore.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ghostdev.explore.ui.presentation.BaseLogic
import com.ghostdev.explore.ui.presentation.base.SplashScreen
import com.ghostdev.explore.ui.presentation.details.DetailsComponent
import com.ghostdev.explore.ui.presentation.home.HomeComponent
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    innerPadding: PaddingValues,
    isDarkTheme: Boolean,
    toggleTheme: () -> Unit
) {
    val navController = rememberNavController()
    val viewmodel: BaseLogic = koinViewModel()

    NavHost(navController = navController, startDestination = NavDestinations.SplashScreen.toString()) {

        composable(NavDestinations.SplashScreen.toString()) {
            SplashScreen(
                onAnimationEnd = {
                    navController.navigate(NavDestinations.Home.toString())
                }
            )
        }
        composable(NavDestinations.Home.toString()) {
            HomeComponent(
                viewmodel,
                navController,
                innerPadding,
                isDarkTheme,
                toggleTheme
            )
        }

        composable(NavDestinations.Details.toString()) {
            DetailsComponent(
                viewmodel,
                navController,
                innerPadding
            )
        }
    }
}