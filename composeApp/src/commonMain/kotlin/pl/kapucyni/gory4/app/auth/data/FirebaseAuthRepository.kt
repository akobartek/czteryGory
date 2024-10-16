package pl.kapucyni.gory4.app.auth.data

import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import pl.kapucyni.gory4.app.auth.domain.AuthRepository
import pl.kapucyni.gory4.app.auth.domain.EmailNotVerifiedException
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.data.COLLECTION_USERS
import pl.kapucyni.gory4.app.common.utils.saveObject

class FirebaseAuthRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): Result<Boolean> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password)
            authResult.user?.let { user ->
                if (user.isEmailVerified || user.email == TEST_EMAIL)
                    Result.success(true)
                else
                    Result.failure(EmailNotVerifiedException())
            } ?: Result.failure(Exception())
        } catch (exc: Exception) {
            Result.failure(exc)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Boolean> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password)
            authResult.user?.let {
                withContext(NonCancellable) {
                    auth.currentUser?.let { user ->
                        user.sendEmailVerification()
                        firestore.saveObject(
                            collectionName = COLLECTION_USERS,
                            id = user.uid,
                            data = User(
                                id = user.uid,
                                email = user.email,
                            )
                        )
                        signOut()
                    }
                    Result.success(true)
                }
            } ?: Result.failure(Exception())
        } catch (exc: Exception) {
            Result.failure(exc)
        }
    }

    override suspend fun sendRecoveryEmail(email: String): Result<Boolean> {
        return try {
            auth.sendPasswordResetEmail(email)
            Result.success(true)
        } catch (exc: Exception) {
            Result.failure(exc)
        }
    }

    override suspend fun sendVerificationEmail() {
        try {
            auth.currentUser?.sendEmailVerification()
        } catch (exc: Exception) {
            // No-op
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    companion object {
        private const val TEST_EMAIL = "test@test.com"
    }
}