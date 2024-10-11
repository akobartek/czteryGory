package pl.kapucyni.gory4.app.common.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Home : Screen()
}