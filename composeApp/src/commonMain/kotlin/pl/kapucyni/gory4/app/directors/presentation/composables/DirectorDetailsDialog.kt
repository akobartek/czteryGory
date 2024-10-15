package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.cd_director_email
import czterygory.composeapp.generated.resources.cd_director_phone
import org.jetbrains.compose.resources.stringResource
import pl.kapucyni.gory4.app.common.presentation.composables.FullScreenDataDialog
import pl.kapucyni.gory4.app.directors.domain.model.Director

@Composable
fun DirectorDetailsDialog(
    director: Director?,
    onDismiss: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    FullScreenDataDialog(
        isVisible = director != null,
        onDismiss = onDismiss,
        actions = {
            if (director?.phoneNumber?.isNotBlank() == true) {
                val phone = director.phoneCountryCode + director.phoneNumber
                IconButton(onClick = { uriHandler.openUri("tel://$phone") }) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(Res.string.cd_director_phone),
                    )
                }
            }
            if (director?.emailAddress?.isNotBlank() == true) {
                IconButton(onClick = { uriHandler.openUri("mailto:${director.emailAddress}") }) {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = stringResource(Res.string.cd_director_email),
                    )
                }
            }
        },
        content = {

        }
    )
}