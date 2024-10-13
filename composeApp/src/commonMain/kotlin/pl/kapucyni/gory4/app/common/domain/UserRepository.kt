package pl.kapucyni.gory4.app.common.domain

import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.common.domain.model.User

interface UserRepository {
    fun getCurrentUser(): Flow<User?>
    suspend fun signOut()
}