package pl.kapucyni.gory4.app.common.utils

import androidx.navigation.NavHostController

fun NavHostController.navigateSafely(route: String) =
    navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateUpSafely(sourceRoute: String) =
    if (currentDestination?.route == sourceRoute) navigateUp() else false