package com.example.integradoramovil.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.ui.theme.BackgroundCard
import com.example.integradoramovil.ui.theme.TextMain
import com.example.integradoramovil.ui.theme.TextSub
import com.example.integradoramovil.ui.theme.PrimaryOrange
import com.example.integradoramovil.ui.theme.ErrorRed
import com.example.integradoramovil.ui.theme.baloo

@Composable
fun MascotaCard(
    mascota: Mascota,
    onClick: () -> Unit,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    val accentColor = PrimaryOrange

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier.size(64.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = accentColor.copy(alpha = 0.1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gato),
                        contentDescription = null,
                        modifier = Modifier.padding(12.dp),
                        tint = accentColor
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = mascota.nombre,
                        fontFamily = baloo,
                        fontSize = 24.sp,
                        color = TextMain,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "${mascota.raza?.nombre ?: "Sin raza"} • ${mascota.sexo ?: "N/A"}",
                        fontSize = 14.sp,
                        color = TextSub,
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        tint = TextSub.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TextSub.copy(alpha = 0.1f)))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(TextSub.copy(alpha = 0.05f))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = accentColor.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = mascota.cliente?.user?.nombre ?: "Sin dueño",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMain.copy(alpha = 0.8f)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ErrorRed.copy(alpha = 0.1f))
                        .clickable { onDelete() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.borrar),
                        contentDescription = "Eliminar",
                        modifier = Modifier.size(18.dp),
                        tint = ErrorRed
                    )
                }
            }
        }
    }
}