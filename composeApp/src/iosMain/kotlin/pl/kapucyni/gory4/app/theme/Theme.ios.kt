package pl.kapucyni.gory4.app.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
internal actual fun SystemMaterialTheme(
    content: @Composable () -> Unit,
    isDark: Boolean
) {
    LaunchedEffect(isDark) {
        UIApplication.sharedApplication.setStatusBarStyle(
            if (isDark) UIStatusBarStyleDarkContent else UIStatusBarStyleLightContent
        )
    }

    MaterialTheme(
        colorScheme = if (isDark) darkScheme else lightScheme,
        content = content
    )
}