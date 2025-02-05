package com.pissartel.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val darkColorScheme = darkColorScheme(
    primary = bulmaColors.cyan,
    onPrimary = White,
    secondary = bulmaColors.pink,
    background = Black,
    onBackground = White,
    surface = Black10,
    onSurface = White,
    surfaceVariant = Black10,
    onSurfaceVariant = White,
    error = Red,
    onError = White,
)

private val lightColorScheme = lightColorScheme(
    primary = bulmaColors.cyan,
    secondary = bulmaColors.pink,
    onPrimary = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
    surfaceVariant = White,
    onSurfaceVariant = Black,
    error = Red40,
    onError = White,
)

@Composable
fun PicSocialAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val statusBarColor = if (darkTheme) Black10 else White
            window.statusBarColor = statusBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    val appColorScheme = when {
        darkTheme -> LocalDarkColorScheme
        else -> LocalLightColorScheme
    }
    CompositionLocalProvider(values = arrayOf(LocalAppColor provides appColorScheme)) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }

}