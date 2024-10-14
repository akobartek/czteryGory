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
import androidx.compose.material3.Text
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
import androidx.navigation.toRoute
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.kapucyni.gory4.app.auth.presentation.AuthScreen
import pl.kapucyni.gory4.app.common.presentation.Screen.Auth
import pl.kapucyni.gory4.app.common.presentation.Screen.DirectorDetails
import pl.kapucyni.gory4.app.common.presentation.Screen.DirectorsList
import pl.kapucyni.gory4.app.common.presentation.Screen.Home
import pl.kapucyni.gory4.app.common.presentation.Screen.UsersList
import pl.kapucyni.gory4.app.common.utils.navigateSafely
import pl.kapucyni.gory4.app.common.utils.navigateUpSafely
import pl.kapucyni.gory4.app.directors.presentation.DirectorsListScreen
import pl.kapucyni.gory4.app.home.presentation.HomeScreen
import pl.kapucyni.gory4.app.theme.AppTheme
import pl.kapucyni.gory4.app.users.presentation.UsersListScreen

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
                        navigate = { screen ->
                            navController.navigateSafely(
                                route = screen,
                                screenToClean = if (screen == Auth) Home else null,
                            )
                        },
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
                        },
                    )
                }

                composable<UsersList> {
                    UsersListScreen(
                        navigateUp = {
                            navController.navigateUpSafely(UsersList::class.simpleName)
                        },
                    )
                }

                composable<DirectorsList> {
                    val args = it.toRoute<DirectorsList>()
                    DirectorsListScreen(
                        navigateUp = {
                            navController.navigateUpSafely(DirectorsList::class.simpleName)
                        },
                        openDetails = { director ->
                            navController.navigateSafely(
                                route = DirectorDetails(director?.id),
                            )
                        },
                        addingEnabled = args.addingEnabled,
                    )
                }

                composable<DirectorDetails> {
                    val args = it.toRoute<DirectorDetails>()
                    Text(args.directorId ?: "null")
                }
            }
        }
    }
}