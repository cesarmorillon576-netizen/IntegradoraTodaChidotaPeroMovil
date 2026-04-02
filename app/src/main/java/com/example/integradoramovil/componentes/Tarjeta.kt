package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.*
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel

// mejorando el ux/ui pq me soy ingeniero de diseño
@Composable
private fun StatusBadge(visibilidad: String) {
    val esVisible = visibilidad.lowercase() == "visible"
    Surface(
        color = (if (esVisible) SuccessGreen else TextSub).copy(alpha = 0.1f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = visibilidad.uppercase(),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
            color = if (esVisible) SuccessGreen else TextSub,
            fontFamily = baloo
        )
    }
}

// --- TARJETA DE RAZA ---
@Composable
fun tarjeta(
    raza: Raza,
    animal: Animal,
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
) {
    val esVisible = raza.visibilidad == "visible"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (esVisible) 1f else 0.6f),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(defaultElevation = if (esVisible) 2.dp else 0.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = raza.nombre,
                    color = TextMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = baloo
                )
                Text(
                    text = "Animal: ${animal.nombre}",
                    color = TextSub,
                    fontSize = 13.sp,
                    fontFamily = baloo
                )
                Spacer(modifier = Modifier.height(6.dp))
                StatusBadge(raza.visibilidad)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if(AuthManager.isAdmin()){
                    IconButton(onClick = { abrirEditar(raza, animal) }) {
                        Icon(painterResource(R.drawable.editar), "Editar", tint = PrimaryOrange, modifier = Modifier.size(22.dp))
                    }
                    IconButton(onClick = { viewModel.cambiarEstado(null, raza) }) {
                        Icon(painterResource(R.drawable.cambiar_estado), "Estado", tint = if (esVisible) PrimaryOrange else TextSub, modifier = Modifier.size(22.dp))
                    }
                    IconButton(onClick = { viewModel.eliminarRaza(raza) }) {
                        Icon(painterResource(R.drawable.borrar), "Borrar", tint = ErrorRed, modifier = Modifier.size(22.dp))
                    }
                }
            }
        }
    }
}

// --- TARJETA DE ANIMAL ---
@Composable
fun tarjeta(
    animal: Animal,
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
) {
    val esVisible = animal.visibilidad == "visible"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(if (esVisible) 1f else 0.6f),
        colors = CardDefaults.cardColors(containerColor = BackgroundCard),
        elevation = CardDefaults.cardElevation(defaultElevation = if (esVisible) 2.dp else 0.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = animal.nombre,
                    color = TextMain,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = baloo
                )
                Text(
                    text = "Dato primario",
                    color = TextSub,
                    fontSize = 13.sp,
                    fontFamily = baloo
                )
                Spacer(modifier = Modifier.height(6.dp))
                StatusBadge(animal.visibilidad ?: "Oculto")
            }

            Row {
                if(AuthManager.isAdmin()){
                    IconButton(onClick = { abrirEditar(null, animal) }) {
                        Icon(painterResource(R.drawable.editar), "Editar", tint = PrimaryOrange, modifier = Modifier.size(22.dp))
                    }
                    IconButton(onClick = { viewModel.cambiarEstado(animal, null) }) {
                        Icon(painterResource(R.drawable.cambiar_estado), "Estado", tint = if (esVisible) PrimaryOrange else TextSub, modifier = Modifier.size(22.dp))
                    }
                    IconButton(onClick = { viewModel.eliminarAnimal(animal) }) {
                        Icon(painterResource(R.drawable.borrar), "Borrar", tint = ErrorRed, modifier = Modifier.size(22.dp))
                    }
                }
            }
        }
    }
}