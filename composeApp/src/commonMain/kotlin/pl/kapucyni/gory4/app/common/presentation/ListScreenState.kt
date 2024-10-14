package pl.kapucyni.gory4.app.common.presentation

import pl.kapucyni.gory4.app.common.domain.model.ListItem

sealed class ListScreenState {
    data object Loading : ListScreenState()
    data class Success(val data: List<ListItem>, val changingData: Boolean) : ListScreenState()
}