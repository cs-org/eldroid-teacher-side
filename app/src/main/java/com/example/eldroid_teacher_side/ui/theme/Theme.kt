package com.example.eldroid_teacher_side.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkTextSecondary,
    tertiary = GoldAccent,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    outline = DarkOutline,
    surfaceVariant = DarkOutline,
    onSurfaceVariant = DarkTextSecondary,
    primaryContainer = Color(0xFF1B3D2F),
    onPrimaryContainer = Color(0xFFE7F0EB),
    secondaryContainer = Color(0xFF004020),
    onSecondaryContainer = Color(0xFFD0E8DC),
    tertiaryContainer = Color(0xFF3E351A),
    onTertiaryContainer = GoldAccent
)

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    tertiary = GoldAccent,
    background = Color(0xFFFDFDFD),
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = GreenPrimary,
    onSurface = GreenPrimary,
    surfaceVariant = Color(0xFFEEEEEE),
    onSurfaceVariant = Color(0xFF444444),
    primaryContainer = Color(0xFFE7F0EB), // Light green derived from primary
    onPrimaryContainer = GreenPrimary,
    secondaryContainer = Color(0xFFD0E8DC),
    onSecondaryContainer = GreenSecondary,
    tertiaryContainer = Color(0xFFFBF3D9), // Light gold
    onTertiaryContainer = GoldAccent
)

@Composable
fun EldroidteachersideTheme(
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
        typography = Typography
    ) {
        // Global Surface to apply background color to the entire app
        Surface(
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}
