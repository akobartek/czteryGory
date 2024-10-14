package pl.kapucyni.gory4.app.users.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.created_at
import czterygory.composeapp.generated.resources.user_type_admin
import czterygory.composeapp.generated.resources.user_type_director
import czterygory.composeapp.generated.resources.user_type_member
import org.jetbrains.compose.resources.stringResource
import pl.kapucyni.gory4.app.common.domain.model.User
import pl.kapucyni.gory4.app.common.domain.model.UserType.*
import pl.kapucyni.gory4.app.common.presentation.composables.HeightSpacer

@Composable
fun UserListItemLayout(
    user: User,
    onTypeChange: (User) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = when (user.userType) {
                NONE -> MaterialTheme.colorScheme.errorContainer
                MEMBER -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.primaryContainer
            }
        ),
        onClick = { focusManager.clearFocus(true) },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                if (user.userType != NONE) {
                    Text(
                        text = when (user.userType) {
                            MEMBER -> stringResource(Res.string.user_type_member)
                            DIRECTOR -> stringResource(Res.string.user_type_director)
                            ADMIN -> stringResource(Res.string.user_type_admin)
                            else -> ""
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                    )
                    HeightSpacer(4.dp)
                }

                SelectionContainer {
                    Text(
                        text = user.email ?: "",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    )
                }
                HeightSpacer(4.dp)
                Text(
                    text = stringResource(Res.string.created_at, user.getFormattedCreatedAt()),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            if (user.userType == NONE || user.userType == MEMBER)
                UserTypeChangeIcon(
                    userType = user.userType,
                    onClick = { onTypeChange(user) },
                )
        }
    }
}