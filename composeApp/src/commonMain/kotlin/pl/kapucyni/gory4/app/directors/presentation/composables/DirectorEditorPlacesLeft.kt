package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.director_places_left
import org.jetbrains.compose.resources.stringResource

@Composable
fun DirectorEditorPlacesLeft(
    placesLeft: Int,
    onValueChange: (Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { onValueChange((placesLeft - 1).coerceIn(0, 25)) }) {
            Icon(Icons.Default.Remove, null)
        }
        OutlinedTextField(
            value = placesLeft.toString(),
            enabled = false,
            onValueChange = {},
            label = { Text(stringResource(Res.string.director_places_left)) },
            modifier = Modifier.width(160.dp)
        )
        IconButton(onClick = { onValueChange((placesLeft + 1).coerceIn(0, 25)) }) {
            Icon(Icons.Default.Add, null)
        }
    }

}