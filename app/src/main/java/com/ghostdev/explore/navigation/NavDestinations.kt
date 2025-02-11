package com.ghostdev.explore.navigation

sealed class NavDestinations(val route: String) {

    data object SplashScreen: NavDestinations("splashscreen")
    data object Home: NavDestinations("home")
    data object Details: NavDestinations("details")

}