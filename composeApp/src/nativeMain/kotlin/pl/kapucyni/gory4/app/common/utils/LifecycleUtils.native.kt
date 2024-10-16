package pl.kapucyni.gory4.app.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

@Composable
actual fun <T> StateFlow<T>.collectAsStateMultiplatform(context: CoroutineContext): State<T> =
    collectAsState(context)

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    // not handled
}