package pl.kapucyni.gory4.app.auth.domain.model

data class User(
    val email: String? = null,
    val userType: UserType = UserType.MEMBER,
    val hasAccess: Boolean = false,
)