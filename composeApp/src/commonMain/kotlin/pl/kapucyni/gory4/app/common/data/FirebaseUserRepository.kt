package pl.kapucyni.gory4.app.common.data

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import pl.kapucyni.gory4.app.auth.domain.model.User
import pl.kapucyni.gory4.app.common.domain.UserRepository
import pl.kapucyni.gory4.app.common.utils.getFirestoreDocument

class FirebaseUserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : UserRepository {

    override fun getCurrentUser(): Flow<User?> =
        auth.currentUser?.let { authUser ->
            firestore.getFirestoreDocument<User?>(
                collectionName = COLLECTION_USERS,
                documentId = authUser.uid
            )
        } ?: flowOf(null)

    override suspend fun signOut() {
        auth.signOut()
    }

    companion object {
        private const val COLLECTION_USERS = "users"
    }
}