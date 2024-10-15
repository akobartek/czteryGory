package pl.kapucyni.gory4.app.home.presentation

import pl.kapucyni.gory4.app.common.domain.model.UserType

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data object UserNotSignedIn : HomeScreenState()
    data class UserSignedIn(val userType: UserType, val userId: String) : HomeScreenState()
}