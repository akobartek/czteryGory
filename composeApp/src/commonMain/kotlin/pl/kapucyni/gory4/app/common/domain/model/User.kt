package pl.kapucyni.gory4.app.common.domain.model

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String? = null,
    val email: String? = null,
    val userType: UserType = UserType.NONE,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
)