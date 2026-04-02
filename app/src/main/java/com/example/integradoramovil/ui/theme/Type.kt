package com.example.integradoramovil.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R

val baloo = FontFamily(
    Font(R.font.balooregular, FontWeight.Normal),
    Font(R.font.balooregular, FontWeight.Bold),
    Font(R.font.balooregular, FontWeight.ExtraBold)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = TextMain
    )
)