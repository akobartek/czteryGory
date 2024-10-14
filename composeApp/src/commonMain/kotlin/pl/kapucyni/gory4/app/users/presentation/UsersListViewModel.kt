package pl.kapucyni.gory4.app.users.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.presentation.ListScreenState
import pl.kapucyni.gory4.app.common.presentation.ListViewModel
import pl.kapucyni.gory4.app.users.domain.usecases.ChangeUserStatusUseCase
import pl.kapucyni.gory4.app.users.domain.usecases.FilterUsersUseCase
import pl.kapucyni.gory4.app.users.domain.usecases.GetUsersUseCase

class UsersListViewModel(
    getUsersUseCase: GetUsersUseCase,
    filterUsersUseCase: FilterUsersUseCase,
    private val changeUserStatusUseCase: ChangeUserStatusUseCase,
) : ListViewModel<User>(getUsersUseCase, filterUsersUseCase) {

    fun onUserTypeChange(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            toggleLoading(true)
            changeUserStatusUseCase(user)
            toggleLoading(false)
        }
    }

    private fun toggleLoading(changingData: Boolean) {
        mutableState.update { currentState ->
            if (currentState is ListScreenState.Success)
                currentState.copy(currentState.data, changingData)
            else currentState
        }
    }
}