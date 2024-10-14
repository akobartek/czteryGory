package pl.kapucyni.gory4.app.users.presentation.composables

import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddModerator
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.change_user_type
import org.jetbrains.compose.resources.stringResource
import pl.kapucyni.gory4.app.common.domain.model.UserType
import pl.kapucyni.gory4.app.common.domain.model.UserType.*

@Composable
fun UserTypeChangeIcon(
    userType: UserType,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = when (userType) {
                NONE -> Icons.Filled.AddModerator
                MEMBER -> Icons.Filled.VerifiedUser
                else -> Icons.Filled.Security
            },
            contentDescription = stringResource(Res.string.change_user_type)
        )
    }
}