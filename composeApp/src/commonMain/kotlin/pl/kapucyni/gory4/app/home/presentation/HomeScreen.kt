package pl.kapucyni.gory4.app.home.presentation

import androidx.compose.runtime.Composable
import pl.kapucyni.gory4.app.common.presentation.Screen
import pl.kapucyni.gory4.app.common.presentation.SnackbarEvent
import pl.kapucyni.gory4.app.common.presentation.composables.LoadingBox

@Composable
fun HomeScreen(
    showSnackbar: (SnackbarEvent) -> Unit,
    navigate: (Screen) -> Unit,
) {
    LoadingBox()
}