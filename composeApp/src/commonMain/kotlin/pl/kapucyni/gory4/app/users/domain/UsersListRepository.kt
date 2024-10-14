package pl.kapucyni.gory4.app.users.domain

import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.common.domain.model.User

interface UsersListRepository {
    fun getUsers(): Flow<List<User>>
    suspend fun changeUserStatus(user: User)
    suspend fun filterUsers(users: List<User>, query: String): List<User>
}