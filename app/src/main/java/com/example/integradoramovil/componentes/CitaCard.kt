package com.example.integradoramovil.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.modelos.Cita
import com.example.integradoramovil.ui.theme.*

@Composable
fun CitaCard(cita: Cita) {
    val estadoColor = when (cita.estado.lowercase()) {
        "agendada" -> Color(0xFF42A5F5)
        "realizada" -> Color(0xFF66BB6A)
        "en_proceso" -> PrimaryOrange
        "cancelada" -> Color(0xFFEF5350)
        else -> TextSub
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = PrimaryOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    val fechaLimpia = cita.fecha?.split("T")?.getOrNull(0) ?: "Sin fecha"
                    Text(
                        text = fechaLimpia,
                        color = TextSub,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = baloo
                    )
                }

                Surface(
                    color = estadoColor.copy(alpha = 0.15f),
                    shape = CircleShape
                ) {
                    Text(
                        text = cita.estado.replace("_", " ").uppercase(),
                        color = estadoColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(PrimaryOrange.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Pets,
                        contentDescription = null,
                        tint = PrimaryOrange,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = cita.mascota?.nombre ?: "Mascota",
                        color = TextMain,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = baloo
                    )
                    Text(
                        text = "Dueño: ${cita.mascota?.cliente?.user?.nombre ?: "N/A"}",
                        color = TextSub,
                        fontSize = 13.sp
                    )
                }
            }

            // Descripción (si existe)
            if (!cita.descripcion.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = Background.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = cita.descripcion,
                        color = TextMain.copy(alpha = 0.8f),
                        fontSize = 13.sp,
                        modifier = Modifier.padding(12.dp),
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}