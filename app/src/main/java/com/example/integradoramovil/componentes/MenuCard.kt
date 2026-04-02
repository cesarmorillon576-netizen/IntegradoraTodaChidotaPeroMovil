package com.example.integradoramovil.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R
import com.example.integradoramovil.ui.theme.*

@Composable
fun MenuCard(
    titulo: String,
    subtitulo: String,
    onClick: () -> Unit,
    iconRes: Int = R.drawable.huella
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(20.dp),
                color = PrimaryOrange.copy(alpha = 0.1f)
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.padding(14.dp),
                    tint = PrimaryOrange
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = titulo,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextMain,
                    fontFamily = baloo
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitulo,
                    fontSize = 14.sp,
                    color = TextSub,
                    lineHeight = 18.sp,
                    maxLines = 2
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.huella),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = PrimaryOrange.copy(alpha = 0.3f)
            )
        }
    }
}