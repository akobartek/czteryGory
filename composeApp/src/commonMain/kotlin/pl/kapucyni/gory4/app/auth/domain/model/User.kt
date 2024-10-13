package pl.kapucyni.gory4.app.auth.domain.model

import kotlinx.serialization.Serializable
import pl.kapucyni.gory4.app.common.domain.model.UserType

@Serializable
data class User(
    val id: String? = null,
    val email: String? = null,
    val userType: UserType = UserType.NONE,
)