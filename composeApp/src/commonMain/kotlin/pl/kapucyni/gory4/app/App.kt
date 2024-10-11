package pl.kapucyni.gory4.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import pl.kapucyni.gory4.app.common.presentation.Screen.Home
import pl.kapucyni.gory4.app.core.presentation.HomeScreen
import pl.kapucyni.gory4.app.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        val navController = rememberNavController()

        Scaffold {
            NavHost(
                navController = navController,
                startDestination = Home,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .windowInsetsPadding(WindowInsets.safeDrawing)
            ) {
                composable<Home> {
                    HomeScreen()
                }
            }
        }
    }
}