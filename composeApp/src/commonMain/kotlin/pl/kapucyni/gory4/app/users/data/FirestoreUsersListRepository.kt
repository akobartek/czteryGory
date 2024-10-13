package pl.kapucyni.gory4.app.users.data

import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import pl.kapucyni.gory4.app.common.data.COLLECTION_USERS
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.utils.getFirestoreCollection
import pl.kapucyni.gory4.app.users.domain.UsersListRepository

class FirestoreUsersListRepository(
    private val firestore: FirebaseFirestore,
): UsersListRepository {

    override fun getUsers(): Flow<List<User>> =
        firestore.getFirestoreCollection(COLLECTION_USERS)

    override suspend fun changeUserStatus(user: User): Boolean {
        TODO("Not yet implemented")
    }
}