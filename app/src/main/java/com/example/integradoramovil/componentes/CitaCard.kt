package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import com.example.integradoramovil.modelos.Cita
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textOrange
import androidx.compose.ui.graphics.Color

@Composable
fun CitaCard(cita: Cita) {
    val estadoColor = when (cita.estado.lowercase()) {
        "agendada" -> Color(0xFF2196F3)
        "realizada" -> Color(0xFF4CAF50)
        "en_proceso" -> Color(0xFFFF9800)
        "cancelada" -> Color(0xFFF44336)
        else -> textColor.copy(alpha = 0.6f)
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = textColorsubMain),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val fechaLimpia = cita.fecha?.split("T")?.getOrNull(0) ?: "Sin fecha"
                Text(
                    text = fechaLimpia,
                    color = textOrange,
                    fontWeight = FontWeight.Bold
                )

                Surface(
                    color = estadoColor.copy(alpha = 0.1f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = cita.estado.uppercase(),
                        color = estadoColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Mascota: ${cita.mascota?.nombre ?: "Desconocida"}",
                color = textColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Dueño: ${cita.mascota?.cliente?.user?.nombre ?: "N/A"}",
                color = textColor.copy(alpha = 0.8f),
                fontSize = 14.sp
            )

            if (!cita.descripcion.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = cita.descripcion,
                    color = textColor.copy(alpha = 0.7f),
                    fontSize = 13.sp,
                    maxLines = 2
                )
            }
        }
    }
}