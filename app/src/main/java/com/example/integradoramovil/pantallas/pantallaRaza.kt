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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.tarjeta
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel

@Composable
fun pantallaRaza(navController: NavController, viewModel: AnimalRazaUserViewModel){
    LaunchedEffect(Unit) {
        viewModel.cargarRazas()
    }

    val razas by viewModel.razas
    val animales by viewModel.animales

    Box(
        modifier = Modifier
            .fillMaxSize().
            background(Background)
    ){
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            items(razas, key = {it.id_raza}){ r ->
                for(a in animales) {
                    if(r.id_animal == a.id_animal)
                        tarjeta(r, a, viewModel)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}