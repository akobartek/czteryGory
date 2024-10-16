package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.cd_director_email
import czterygory.composeapp.generated.resources.cd_director_phone
import czterygory.composeapp.generated.resources.director_email
import czterygory.composeapp.generated.resources.director_location
import czterygory.composeapp.generated.resources.director_phone
import org.jetbrains.compose.resources.stringResource
import pl.kapucyni.gory4.app.common.presentation.composables.FullScreenDataDialog
import pl.kapucyni.gory4.app.common.presentation.composables.HeightSpacer
import pl.kapucyni.gory4.app.directors.domain.model.Director

@Composable
fun DirectorDetailsDialog(
    selectedDirector: Director?,
    onDismiss: () -> Unit,
) {
    var phoneDialogVisible by remember { mutableStateOf(false) }
    var emailDialogVisible by remember { mutableStateOf(false) }

    selectedDirector?.let { director ->
        FullScreenDataDialog(
            isVisible = true,
            onDismiss = onDismiss,
            actions = {
                if (director.phoneNumber.isNotBlank()) {
                    IconButton(onClick = { phoneDialogVisible = true }) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(Res.string.cd_director_phone),
                        )
                    }
                }
                if (director.emailAddress.isNotBlank()) {
                    IconButton(onClick = { emailDialogVisible = true }) {
                        Icon(
                            imageVector = Icons.Default.Mail,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(Res.string.cd_director_email),
                        )
                    }
                }
            },
            content = {
                DirectorPhoto(
                    photoUrl = director.photoUrl,
                    size = 140.dp,
                )
                HeightSpacer(4.dp)
                Text(
                    text = director.name,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    ),
                )
                Text(
                    text = stringResource(
                        Res.string.director_location,
                        director.city,
                        director.region
                    ),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Center,
                    ),
                )
                HeightSpacer(12.dp)
                Text(
                    text = director.description,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 15.sp,
                        textAlign = TextAlign.Justify,
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        )

        DirectorContactDialog(
            isVisible = phoneDialogVisible,
            imageVector = Icons.Default.Call,
            dialogTitleId = Res.string.director_phone,
            dialogText = director.phoneCountryCode + director.phoneNumber,
            uri = PHONE_URI,
            onDismiss = { phoneDialogVisible = false },
        )
        DirectorContactDialog(
            isVisible = emailDialogVisible,
            imageVector = Icons.Default.Mail,
            dialogTitleId = Res.string.director_email,
            dialogText = director.emailAddress,
            uri = EMAIL_URI,
            onDismiss = { emailDialogVisible = false },
        )
    }
}

private const val PHONE_URI = "tel://"
private const val EMAIL_URI = "mailto:"