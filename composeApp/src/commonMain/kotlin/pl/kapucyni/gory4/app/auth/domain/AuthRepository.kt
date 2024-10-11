package pl.kapucyni.gory4.app.auth.domain

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Boolean>
    suspend fun signUp(email: String, password: String): Result<Boolean>
    suspend fun sendRecoveryEmail(email: String): Result<Boolean>
    suspend fun sendVerificationEmail()
    suspend fun signOut()
}