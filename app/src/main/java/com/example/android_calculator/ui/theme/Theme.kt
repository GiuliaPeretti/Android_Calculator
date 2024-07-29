package com.example.android_calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.Night.background,
    primary = ThemeColors.Night.primary,
    secondary = ThemeColors.Night.secondary,
    tertiary = ThemeColors.Night.tertiary,
    onBackground = ThemeColors.Night.text
)

private val LightColorScheme = lightColorScheme(
    background = ThemeColors.Day.background,
    primary = ThemeColors.Day.primary,
    secondary = ThemeColors.Day.secondary,
    tertiary = ThemeColors.Day.tertiary,
    onBackground = ThemeColors.Day.text

    /*
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Android_calculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
): ColorScheme {
    val colorScheme = if(darkTheme) DarkColorScheme else LightColorScheme
    return colorScheme
}