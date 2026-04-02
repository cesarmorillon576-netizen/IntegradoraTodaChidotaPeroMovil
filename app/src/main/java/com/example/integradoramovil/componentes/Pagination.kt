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
// Importamos la nueva paleta
import com.example.integradoramovil.ui.theme.BackgroundCard
import com.example.integradoramovil.ui.theme.PrimaryOrange
import com.example.integradoramovil.ui.theme.TextMain
import com.example.integradoramovil.ui.theme.TextSub

@Composable
fun Pagination(
    actual: Int,
    total: Int,
    onPageChange: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = BackgroundCard,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onPageChange(actual - 1) },
                enabled = actual > 1,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = PrimaryOrange,
                    disabledContentColor = TextSub.copy(alpha = 0.3f)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Anterior",
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "PÁGINA",
                    color = TextSub,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$actual",
                        color = PrimaryOrange,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = " / $total",
                        color = TextMain.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            IconButton(
                onClick = { onPageChange(actual + 1) },
                enabled = actual < total,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = PrimaryOrange,
                    disabledContentColor = TextSub.copy(alpha = 0.3f)
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Siguiente",
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}