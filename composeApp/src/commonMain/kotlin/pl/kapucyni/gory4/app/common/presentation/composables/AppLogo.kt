package pl.kapucyni.gory4.app.common.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import czterygory.composeapp.generated.resources.Res
import czterygory.composeapp.generated.resources.logo_full
import org.jetbrains.compose.resources.painterResource

@Composable
fun AppLogo(
    size: Dp = 160.dp,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(Res.drawable.logo_full),
        contentDescription = null,
        colorFilter =
        if (isSystemInDarkTheme()) ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
        else null,
        modifier = modifier.width(size),
    )
}