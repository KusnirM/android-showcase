package com.mk.androidshowcase.presentation.foundation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier

@Composable
fun AppTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val colors = if (isDark) DarkColorPalette else LightColorPalette

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colorScheme = colors.material,
            typography = Typography,
            shapes = MaterialTheme.shapes,
            content = {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize(),
                    content = content
                )
            }
        )
    }
}

private val LocalColors = staticCompositionLocalOf { LightColorPalette }

val MaterialTheme.appColorScheme: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

private val LightColorPalette = AppColors(
    material = lightColorScheme(
        primary = PrimaryLight,
        onPrimary = Neutral0Light,
        primaryContainer = PrimaryContainerLight,
        onPrimaryContainer = Neutral0Light,
        secondary = SecondaryLight,
        onSecondary = Neutral0Light,
        secondaryContainer = SecondaryContainerLight,
        onSecondaryContainer = Neutral0Light,
        background = BackgroundLight,
        onBackground = Neutral100Light,
        surface = SurfaceLight,
        onSurface = Neutral100Light,
        error = ErrorLight,
        onError = Neutral0Light,
        errorContainer = ErrorContainerLight,
        onErrorContainer = Neutral0Light,
        surfaceTint = Neutral0Light,
        surfaceVariant = Neutral80Light,
    ),
    neutral0 = Neutral0Light,
    neutral20 = Neutral20Light,
    neutral40 = Neutral40Light,
    neutral60 = Neutral60Light,
    neutral80 = Neutral80Light,
    neutral100 = Neutral100Light,
    success = SuccessLight,
    warning = WarningLight,
)

private val DarkColorPalette = AppColors(
    material = darkColorScheme(
        primary = PrimaryDark,
        onPrimary = Neutral0Dark,
        primaryContainer = PrimaryContainerDark,
        onPrimaryContainer = Neutral100Dark,
        secondary = SecondaryDark,
        onSecondary = Neutral0Dark,
        secondaryContainer = SecondaryContainerDark,
        onSecondaryContainer = Neutral0Dark,
        background = BackgroundDark,
        onBackground = Neutral100Dark,
        surface = SurfaceDark,
        onSurface = Neutral100Dark,
        error = ErrorDark,
        onError = Neutral0Dark,
        errorContainer = ErrorContainerDark,
        onErrorContainer = Neutral100Dark,
        surfaceTint = Neutral0Dark,
        surfaceVariant = Neutral40Dark,
    ),
    neutral0 = Neutral0Dark,
    neutral20 = Neutral20Dark,
    neutral40 = Neutral40Dark,
    neutral60 = Neutral60Dark,
    neutral80 = Neutral80Dark,
    neutral100 = Neutral100Dark,
    success = SuccessDark,
    warning = WarningDark,
)
