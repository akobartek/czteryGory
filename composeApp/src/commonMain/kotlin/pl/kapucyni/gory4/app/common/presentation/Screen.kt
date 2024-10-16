package pl.kapucyni.gory4.app.common.presentation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object Auth : Screen()

    @Serializable
    data object Home : Screen()

    @Serializable
    data object UsersList : Screen()

    @Serializable
    data class DirectorsList(val isAdmin: Boolean) : Screen()

    @Serializable
    data class DirectorDetails(val directorId: String? = null) : Screen()

    @Serializable
    data class DirectorEditor(
        val directorId: String? = null,
        val isAdmin: Boolean = false
    ) : Screen()
}