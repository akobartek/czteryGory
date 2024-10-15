package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.empty_field_error
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DirectorEditorTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: StringResource,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Words,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean,
    errorMessageRes: StringResource = Res.string.empty_field_error,
    maxLines: Int = 1,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelRes)) },
        keyboardOptions = KeyboardOptions(
            capitalization = capitalization,
            keyboardType = keyboardType,
        ),
        isError = isError,
        singleLine = maxLines == 1,
        supportingText = if (isError) {
            {
                Text(text = stringResource(errorMessageRes))
            }
        } else null,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}