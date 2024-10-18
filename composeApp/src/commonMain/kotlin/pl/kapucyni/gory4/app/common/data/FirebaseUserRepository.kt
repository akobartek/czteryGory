package pl.kapucyni.gory4.app.common.data

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import pl.kapucyni.gory4.app.common.domain.model.User
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

    override suspend fun deleteAccount() {
        try {
            withContext(NonCancellable) {
                auth.currentUser?.let { user ->
                    firestore.collection(COLLECTION_USERS)
                        .document(user.uid)
                        .delete()
                    user.delete()
                }
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }
}