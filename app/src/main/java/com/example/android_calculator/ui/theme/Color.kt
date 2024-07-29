package com.example.android_calculator.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val lightPink = Color(0xFF6b4c61)
val brightPink = Color(0xFFd91698)
val darkerPink = Color(0xFF210c1a)
val darkPink = Color(0xFF3b2835)

val lilac = Color(0xFF5f4673)

sealed class ThemeColors(
        val background: Color,
        val secondary: Color,
        val primary: Color,
        val tertiary: Color
    ) {
        object Night : ThemeColors(
            background = Color(0xFF000000),
            primary = Color(0xFF4FB64C),
            secondary = Color(0xFF4FB64C),
            tertiary = Color(0xffffffff)
        )
        object Day : ThemeColors(
            background = Color(0xFF000000),
            primary = Color(0xFF4FB64C),
            secondary = Color(0xFF4FB64C),
            tertiary = Color(0xffffffff)
        )
    }
}