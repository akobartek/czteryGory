package pl.kapucyni.gory4.app.common.presentation.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppAlertDialog(
    isVisible: Boolean,
    imageVector: ImageVector,
    dismissible: Boolean = true,
    dialogTitleId: StringResource,
    dialogTextId: StringResource,
    confirmBtnTextId: StringResource,
    onConfirm: () -> Unit,
    dismissBtnTextId: StringResource? = null,
    onDismissRequest: () -> Unit = {},
) {
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
                    text = stringResource(dialogTextId),
                    style = MaterialTheme.typography.labelLarge.copy(
                        textAlign = TextAlign.Justify,
                    )
                )
            },
            onDismissRequest = { if (dismissible) onDismissRequest() },
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = stringResource(confirmBtnTextId))
                }
            },
            dismissButton = {
                dismissBtnTextId?.let {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = stringResource(dismissBtnTextId))
                    }
                }
            }
        )
}