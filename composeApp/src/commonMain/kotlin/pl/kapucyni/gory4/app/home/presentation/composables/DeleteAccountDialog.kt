package pl.kapucyni.gory4.app.home.presentation.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.runtime.Composable
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.cancel
import czterygory.composeapp.generated.resources.delete
import czterygory.composeapp.generated.resources.delete_account
import czterygory.composeapp.generated.resources.delete_account_msg
import pl.kapucyni.gory4.app.common.presentation.composables.AppAlertDialog

@Composable
fun DeleteAccountDialog(
    isVisible: Boolean,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
) {
    AppAlertDialog(
        isVisible = isVisible,
        imageVector = Icons.Default.PersonOff,
        dismissible = false,
        dialogTitleId = Res.string.delete_account,
        dialogTextId = Res.string.delete_account_msg,
        confirmBtnTextId = Res.string.delete,
        onConfirm = {
            onDismiss()
            onDelete()
        },
        dismissBtnTextId = Res.string.cancel,
        onDismissRequest = onDismiss
    )
}