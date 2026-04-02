package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.integradoramovil.componentes.tarjeta
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import androidx.compose.runtime.collectAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaAnimal(
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
){

    val animales by viewModel.animales.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        items(
            items = animales,
            key = { animal: Animal -> animal.id }
        ) { animal: Animal ->
            tarjeta(animal, viewModel, abrirEditar)
        }
    }

}