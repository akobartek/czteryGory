package pl.kapucyni.gory4.app.common.utils

import androidx.navigation.NavHostController
import pl.kapucyni.gory4.app.common.presentation.Screen

fun NavHostController.navigateSafely(route: Screen, screenToClean: Screen? = null) =
    navigate(route) {
        launchSingleTop = true
        restoreState = true

        screenToClean?.let { currentScreen ->
            popUpTo(currentScreen) {
                inclusive = true
            }
        }
    }

fun NavHostController.navigateUpSafely(sourceName: String?) =
    if (currentDestination?.route?.contains(sourceName ?: "") == true) navigateUp() else false