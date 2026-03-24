package com.example.studysnap.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Teal,
    onPrimary = White,
    primaryContainer = TealContainer,
    onPrimaryContainer = TealDark,
    secondary = NavyDark,
    onSecondary = White,
    secondaryContainer = NavySurface,
    onSecondaryContainer = SidebarTextActive,
    tertiary = QuizAmber,
    onTertiary = DarkText,
    tertiaryContainer = QuizAmberBg,
    onTertiaryContainer = DarkText,
    background = GrayBackground,
    onBackground = DarkText,
    surface = White,
    onSurface = DarkText,
    surfaceVariant = GrayCard,
    onSurfaceVariant = GrayText,
    outline = GrayBorder,
    outlineVariant = GrayBorder,
    error = ErrorRed,
    onError = White,
    errorContainer = ErrorRedLight,
    onErrorContainer = Color(0xFF991B1B)
)

private val DarkColorScheme = darkColorScheme(
    primary = Teal,
    onPrimary = NavyDark,
    primaryContainer = TealDark,
    onPrimaryContainer = TealLight,
    secondary = NavyMedium,
    onSecondary = White,
    secondaryContainer = NavyDark,
    onSecondaryContainer = SidebarText,
    tertiary = QuizAmber,
    onTertiary = DarkText,
    tertiaryContainer = Color(0xFF3D3000),
    onTertiaryContainer = QuizAmberBg,
    background = Color(0xFF111318),
    onBackground = Color(0xFFE5E7EB),
    surface = Color(0xFF171923),
    onSurface = Color(0xFFE5E7EB),
    surfaceVariant = NavySurface,
    onSurfaceVariant = SidebarText,
    outline = NavySurface,
    outlineVariant = Color(0xFF374151),
    error = Color(0xFFF87171),
    onError = Color(0xFF450A0A),
    errorContainer = Color(0xFF7F1D1D),
    onErrorContainer = ErrorRedLight
)

@Composable
fun StudySnapTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}