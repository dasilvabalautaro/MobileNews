package com.globalhiddenodds.mobilenews.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = GREY500,
    primaryVariant = Grey600,
    secondary = Green600,
    secondaryVariant = Green900,
    error = Error,
    background = Background,
    surface = SurfaceNight,
    onPrimary = OnPrimaryNight,
    onSecondary = OnSecondary,
    onBackground = OnBackgroundNight,
    onSurface = OnSurfaceNight,
)

private val LightColorPalette = lightColors(
    primary = Grey400,
    primaryVariant = Grey600,
    secondary = Green600,
    secondaryVariant = Green900,
    error = Error,
    background = Background,
    surface = SurfaceNight,
    onPrimary = OnPrimaryDay,
    onSecondary = OnSecondary,
    onBackground = OnBackgroundNight,
    onSurface = OnSurfaceNight,
)

@Composable
fun MobileNewsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}