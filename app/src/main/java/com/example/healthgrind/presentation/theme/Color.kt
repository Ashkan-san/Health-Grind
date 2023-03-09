package com.example.healthgrind.presentation.theme

import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors

val Red400 = Color(0xFFCF6679)

val GrindBlue = Color(0xff504c83)
val GrindBlueDark = Color(0xFF2A2842)
val HealthPink = Color(0xFFB48F8E)
val HealthPinkDark = Color(0xffd8775c)

internal val wearColorPalette: Colors = Colors(
    // Meistgenutzt und Sekundär
    // Varianten sind dunkel/hell
    // On ist Textfarbe auf dem Element zb
    primary = GrindBlue,
    primaryVariant = GrindBlueDark,
    onPrimary = Color.White,

    secondary = HealthPink,
    secondaryVariant = HealthPinkDark,
    onSecondary = Color.White,

    error = Red400,
    onError = Color.White,

    background = Color.Black
)