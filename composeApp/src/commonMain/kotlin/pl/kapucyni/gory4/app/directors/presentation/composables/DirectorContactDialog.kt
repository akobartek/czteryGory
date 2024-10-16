package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.copy
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DirectorContactDialog(
    isVisible: Boolean,
    imageVector: ImageVector,
    dialogTitleId: StringResource,
    dialogText: String,
    uri: String,
    onDismiss: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val clipboard = LocalClipboardManager.current

    if (isVisible)
        AlertDialog(
            icon = {
                Icon(imageVector = imageVector, contentDescription = null)
            },
            title = {
                Text(
                    text = stringResource(dialogTitleId),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        textAlign = TextAlign.Center
                    ),
                )
            },
            text = {
                Text(
                    text = dialogText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center,
                    ),
                    modifier = Modifier
                        .clickable { uriHandler.openUri("$uri$dialogText") }
                        .fillMaxWidth()
                )
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    clipboard.setText(AnnotatedString(dialogText))
                    onDismiss()
                }) {
                    Text(text = stringResource(Res.string.copy))
                }
            }
        )
}