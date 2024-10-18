package pl.kapucyni.gory4.app.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.common.domain.UserRepository

class HomeViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private var userJob: Job? = null
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        userJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.getCurrentUser()
                    .shareIn(this, SharingStarted.Lazily, 1)
                    .collect { user ->
                        _state.update {
                            user?.let {
                                HomeScreenState.UserSignedIn(
                                    userId = it.userId,
                                    userType = it.userType,
                                )
                            } ?: HomeScreenState.UserNotSignedIn
                        }
                    }
            } catch (_: Exception) {
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userJob?.cancel()
            userRepository.signOut()
            _state.update { HomeScreenState.UserNotSignedIn }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            userJob?.cancel()
            userRepository.deleteAccount()
            _state.update { HomeScreenState.UserNotSignedIn }
        }
    }
}