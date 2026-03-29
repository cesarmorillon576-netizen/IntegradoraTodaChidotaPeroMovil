package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(titulo: String, subtitulo: String, onCLick: () -> Unit){
    ElevatedCard(
        onClick = onCLick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = textColorsubMain
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)){
            Text(titulo, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(subtitulo, fontSize = 14.sp, color = textOrange)
        }
    }
}