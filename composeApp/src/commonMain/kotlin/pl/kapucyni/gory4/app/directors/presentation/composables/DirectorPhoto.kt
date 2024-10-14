package pl.kapucyni.gory4.app.directors.presentation.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun DirectorPhoto(
    photoUrl: String?,
    size: Dp = 80.dp
) {
    val placeholder = rememberVectorPainter(Icons.Default.AccountCircle)
    if (photoUrl.isNullOrBlank()) {
        Icon(
            painter = placeholder,
            contentDescription = null,
            modifier = Modifier.size(size)
        )
    } else {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = placeholder,
            placeholder = placeholder,
            modifier = Modifier
                .size(size)
                .clip(CircleShape),
        )
    }
}