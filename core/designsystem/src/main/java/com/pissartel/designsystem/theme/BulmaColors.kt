package com.pissartel.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

class BulmaColors {
    val pink = Color(0xFFDF98C0)
    val cyan = Color(0xFF4A909A)
}

val LocaleBulmaColors = compositionLocalOf { BulmaColors() }
val bulmaColors = BulmaColors()

val MaterialTheme.cryptoColors: BulmaColors
    @Composable
    @ReadOnlyComposable
    get() = LocaleBulmaColors.current