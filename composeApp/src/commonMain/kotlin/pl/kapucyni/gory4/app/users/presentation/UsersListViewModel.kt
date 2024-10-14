package pl.kapucyni.gory4.app.users.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.presentation.ListScreenState
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

@OptIn(FlowPreview::class)
class UsersListViewModel(
    private val usersListRepository: UsersListRepository,
) : ViewModel() {

    private var currentUserList = listOf<User>()
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
                        currentUserList = users
                        filterUsers()
                    }
            } catch (_: Exception) {
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery
                .onEach { _state.update { ListScreenState.Loading } }
                .debounce { query ->
                    if (query.isNotBlank()) 666L // PDK
                    else 0L
                }
                .collect { filterUsers() }
        }
    }

    private suspend fun filterUsers() {
        val filteredList = usersListRepository.filterUsers(currentUserList, searchQuery.value)
        _state.update {
            ListScreenState.Success(filteredList, false)
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.update { query }
    }

    fun onUserTypeChange(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleLoading(true)
            usersListRepository.changeUserStatus(user)
            toggleLoading(false)
        }
    }

    private fun toggleLoading(changingData: Boolean) {
        _state.update { currentState ->
            if (currentState is ListScreenState.Success)
                currentState.copy(currentState.data, changingData)
            else currentState
        }
    }
}