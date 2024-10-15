package pl.kapucyni.gory4.app.common.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.cd_close_dialog
import org.jetbrains.compose.resources.stringResource

@Composable
fun FullScreenDataDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    if (isVisible)
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(Res.string.cd_close_dialog),
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    actions()
                }
                Column(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 12.dp, end = 12.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    content()
                }
            }
        }
}