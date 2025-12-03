package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.tarjeta
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaAnimal(navController: NavController, viewModel: AnimalRazaUserViewModel){


    LaunchedEffect(Unit) {
        viewModel.cargarAnimales()
    }

    val animales by viewModel.animales

    Box(
        modifier = Modifier
            .fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
        ) {
            items(animales, key = {it.id_animal}){ a ->
                tarjeta(a, viewModel)
                Spacer(modifier = Modifier.height(10.dp))

            }
        }
    }

}