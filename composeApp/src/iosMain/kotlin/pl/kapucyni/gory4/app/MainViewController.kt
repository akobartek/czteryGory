package pl.kapucyni.gory4.app

import androidx.compose.ui.window.ComposeUIViewController
import pl.kapucyni.gory4.app.common.utils.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }