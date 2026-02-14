package com.example.integradoramovil.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.tarjeta
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel

@Composable
fun pantallaRaza(
    navController: NavController,
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
){
    LaunchedEffect(Unit) {
        viewModel.cargarRazas()
    }

    val razas by viewModel.razas.collectAsState()
    val animales by viewModel.animales.collectAsState()

    val animalesMap = animales.associateBy { it.id_animal }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ){
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            items(razas, key = {it.id_raza}){ r ->
                animalesMap[r.id_animal]?.let { animal ->
                    tarjeta(r, animal, viewModel, abrirEditar = abrirEditar)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

