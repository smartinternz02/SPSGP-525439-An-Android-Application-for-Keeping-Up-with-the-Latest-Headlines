package com.example.newswiz.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.newswiz.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Roboto_Slab = FontFamily(
    Font(R.font.robotoslab_medium),
)

val Roboto_Slab_Regular = FontFamily(
    Font(R.font.robotoslab_regular)
)

val Roboto_Slab_Bold = FontFamily(
    Font(R.font.robotoslab_bold)
)

val mon_regular = FontFamily(
    Font(R.font.montserrat_regular)
)

val mon_medium = FontFamily(
    Font(R.font.montserrat_medium)
)

val mon_bold = FontFamily(
    Font(R.font.montserrat_bold)
)

val ws_regular = FontFamily(
    Font(R.font.worksans_regular)
)

val ws_medium = FontFamily(
    Font(R.font.worksans_medium)
)

val ws_bold = FontFamily(
    Font(R.font.worksans_bold)
)