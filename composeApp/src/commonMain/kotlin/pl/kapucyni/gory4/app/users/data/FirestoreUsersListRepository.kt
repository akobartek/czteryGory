package pl.kapucyni.gory4.app.users.data

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.common.data.COLLECTION_USERS
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.domain.model.UserType
import pl.kapucyni.gory4.app.common.utils.getFirestoreCollection
import pl.kapucyni.gory4.app.common.utils.saveObject
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

class FirestoreUsersListRepository(
    private val firestore: FirebaseFirestore,
) : UsersListRepository {

    override fun getUsers(): Flow<List<User>> =
        firestore.getFirestoreCollection(COLLECTION_USERS)

    override suspend fun changeUserStatus(user: User) {
        val changedUser = user.copy(
            userType = when (user.userType) {
                UserType.NONE -> UserType.MEMBER
                UserType.MEMBER -> UserType.NONE
                else -> user.userType
            }
        )
        changedUser.id?.let { userId ->
            firestore.saveObject(
                collectionName = COLLECTION_USERS,
                id = userId,
                data = changedUser,
            )
        }
    }

    override suspend fun filterUsers(users: List<User>, query: String): List<User> =
        users.filter { it.email?.contains(query) == true }
            .sortedByDescending { it.createdAt }
}