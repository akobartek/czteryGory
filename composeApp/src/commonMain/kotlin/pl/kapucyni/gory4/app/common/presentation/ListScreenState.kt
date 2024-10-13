package pl.kapucyni.gory4.app.common.presentation

sealed class ListScreenState {
    data object Loading : ListScreenState()
    data class Success<T>(val data: List<T>) : ListScreenState()
}