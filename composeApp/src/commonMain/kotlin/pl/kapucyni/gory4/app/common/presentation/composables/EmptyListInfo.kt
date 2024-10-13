package pl.kapucyni.gory4.app.common.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PsychologyAlt
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.empty_search_list
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyListInfo(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(32.dp)
    ) {
        Image(
            imageVector = Icons.Filled.PsychologyAlt,
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )
        HeightSpacer(4.dp)
        Text(
            text = stringResource(Res.string.empty_search_list),
            style = MaterialTheme.typography.titleLarge.copy(
                textAlign = TextAlign.Center,
            ),
        )
    }
}