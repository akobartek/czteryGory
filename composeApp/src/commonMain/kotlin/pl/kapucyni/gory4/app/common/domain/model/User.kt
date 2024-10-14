package pl.kapucyni.gory4.app.common.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import pl.kapucyni.gory4.app.common.utils.getFormattedDateTime

@Serializable
data class User(
    val id: String = "",
    val email: String? = null,
    val userType: UserType = UserType.NONE,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
): ListItem(id) {

    fun getFormattedCreatedAt(): String =
        Instant.fromEpochMilliseconds(createdAt)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .getFormattedDateTime()
}