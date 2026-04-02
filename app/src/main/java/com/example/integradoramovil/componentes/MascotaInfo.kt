package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MascotaInfo(
    mascota: Mascota,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = BackgroundCard,
        shape = RoundedCornerShape(28.dp),
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cerrar", color = PrimaryOrange, fontWeight = FontWeight.Bold)
            }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Surface(
                    modifier = Modifier.size(80.dp),
                    shape = RoundedCornerShape(24.dp),
                    color = PrimaryOrange.copy(alpha = 0.1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gato),
                        contentDescription = null,
                        modifier = Modifier.padding(16.dp),
                        tint = PrimaryOrange
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = mascota.nombre,
                    fontFamily = baloo,
                    fontSize = 28.sp,
                    color = TextMain,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    InfoItem(label = "Sexo", value = mascota.sexo ?: "N/A")
                    InfoItem(label = "Peso", value = "${mascota.peso ?: 0.0} kg")
                }

                HorizontalDivider(color = TextSub.copy(alpha = 0.1f))

                DetailRow(label = "Raza", value = mascota.raza?.nombre ?: "Sin definir")
                DetailRow(label = "Especie", value = mascota.raza?.animal?.nombre ?: "No especificada")
                if(AuthManager.isAdmin()){
                    DetailRow(label = "Dueño", value = mascota.cliente?.user?.nombre ?: "Sin dueño")
                }


                if (!mascota.descripcion.isNullOrBlank()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Descripción", color = TextSub, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = mascota.descripcion,
                        color = TextMain,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    )
}

@Composable
fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = TextSub, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        Text(text = value, color = PrimaryOrange, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = TextSub, fontSize = 14.sp)
        Text(text = value, color = TextMain, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}