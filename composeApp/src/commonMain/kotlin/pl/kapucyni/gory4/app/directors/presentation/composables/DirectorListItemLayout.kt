package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import czterygory.composeapp.generated.resources.director_location
import org.jetbrains.compose.resources.stringResource
import pl.kapucyni.gory4.app.common.presentation.composables.WidthSpacer
import pl.kapucyni.gory4.app.directors.domain.model.Director

@Composable
fun DirectorListItemLayout(
    director: Director,
    onClick: (Director) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        onClick = {
            focusManager.clearFocus(true)
            onClick(director)
        },
        enabled = director.isAvailable(),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            DirectorPhoto(director.photoUrl)
            WidthSpacer(8.dp)
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = director.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                )
                Text(
                    text = stringResource(
                        Res.string.director_location,
                        director.city,
                        director.region
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                )
            }
        }
    }
}