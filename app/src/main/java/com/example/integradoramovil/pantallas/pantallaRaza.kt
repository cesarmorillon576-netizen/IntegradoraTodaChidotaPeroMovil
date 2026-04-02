package com.example.integradoramovil.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
){

    val razas by viewModel.razas.collectAsState()
    val animales by viewModel.animales.collectAsState()

    val animalesMap = animales.associateBy { it.id }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp, end = 16.dp, start = 16.dp)
    ) {
        items(razas, key = {it.id}){ r ->
            val animalRelacionado = animalesMap[r.animal_id]
            tarjeta(r, animalRelacionado ?: Animal(0, "Desconocido", ""),
                viewModel, abrirEditar)
        }
    }
}

