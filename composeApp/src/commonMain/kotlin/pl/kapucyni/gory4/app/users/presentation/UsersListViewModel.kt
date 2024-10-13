package pl.kapucyni.gory4.app.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.common.presentation.ListScreenState
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

class UsersListViewModel(
    private val usersListRepository: UsersListRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val state: StateFlow<ListScreenState> = _state.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                usersListRepository.getUsers()
                    .shareIn(this, SharingStarted.Lazily, 1)
                    .collect { users ->
                        _state.update { ListScreenState.Success(users) }
                    }
            } catch (_: Exception) {
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }
}