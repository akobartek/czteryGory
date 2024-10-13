package pl.kapucyni.gory4.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.kapucyni.gory4.app.common.presentation.Screen.Auth
import pl.kapucyni.gory4.app.common.presentation.Screen.Home
import pl.kapucyni.gory4.app.auth.presentation.AuthScreen
import pl.kapucyni.gory4.app.common.utils.navigateSafely
import pl.kapucyni.gory4.app.home.presentation.HomeScreen
import pl.kapucyni.gory4.app.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()
        val snackbarHostState = remember { SnackbarHostState() }
        var snackbarMessageRes by remember { mutableStateOf<StringResource?>(null) }
        val snackbarMessage = snackbarMessageRes?.let { stringResource(it) }

        LaunchedEffect(snackbarMessage) {
            snackbarMessage?.let { message ->
                val result = snackbarHostState.showSnackbar(
                    message = message,
                    withDismissAction = true,
                )
                if (result == SnackbarResult.Dismissed)
                    snackbarMessageRes = null
            } ?: snackbarHostState.currentSnackbarData?.dismiss()
        }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = Home,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                composable<Home> {
                    HomeScreen(
                        showSnackbar = { event ->
                            snackbarMessageRes = event.res
                        },
                        navigate = { screen ->
                            navController.navigateSafely(
                                route = screen,
                                screenToClean = if (screen == Auth) Home else null,
                            )
                        }
                    )
                }
                composable<Auth> {
                    AuthScreen(
                        showSnackbar = { event ->
                            snackbarMessageRes = event.res
                        },
                        navigateHome = {
                            navController.navigateSafely(
                                route = Home,
                                screenToClean = Auth,
                            )
                        }
                    )
                }
            }
        }
    }
}