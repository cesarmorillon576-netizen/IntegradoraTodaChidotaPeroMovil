package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.ui.theme.buttonColor
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textOrange

@Composable
fun Pagination(
    actual: Int,
    total: Int,
    onPageChange: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = textColorsubMain,
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón Anterior
            IconButton(
                onClick = { onPageChange(actual - 1) },
                enabled = actual > 1,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = textOrange,
                    disabledContentColor = Color.Gray
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "Anterior")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Página",
                    color = textColor.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
                Text(
                    text = "$actual de $total",
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = { onPageChange(actual + 1) },
                enabled = actual < total,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = textOrange,
                    disabledContentColor = Color.Gray
                )
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "Siguiente")
            }
        }
    }
}