package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.modal
import com.example.integradoramovil.componentes.modaleditar
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.buttonColor
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textOrange
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Catalogos(navController: NavController) {
    val context = LocalContext.current
    val viewModel: AnimalRazaUserViewModel = viewModel(
        factory = AnimalRazaUserViewModel.create(context)
    )
    var catalogoSeleccionado by remember { mutableStateOf("animales") }
    var mostrarModal by remember { mutableStateOf(false) }
    var mostrarModalEditar by remember {mutableStateOf(false)}
    var razaSeleccionada by remember { mutableStateOf<Raza?>(null) }
    var animalSeleccionado by remember {mutableStateOf<Animal?>(null)}
    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (catalogoSeleccionado == "animales") "Catalogo: Animales" else "Catalogo: Razas",
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = textColorsubMain)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = textColorsubMain) {
                NavigationBarItem(
                    selected = catalogoSeleccionado == "animales",
                    onClick = { catalogoSeleccionado = "animales" },
                    label = { Text("Animales") },
                    icon = { Icon(Icons.Default.Pets, contentDescription = null) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = textOrange,
                        selectedTextColor = textOrange,
                        unselectedTextColor = textColor
                    ),
                )
                NavigationBarItem(
                    selected = catalogoSeleccionado == "razas",
                    onClick = { catalogoSeleccionado = "razas" },
                    label = { Text("Razas") },
                    icon = { Icon(Icons.Default.Pets, contentDescription = null) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = textOrange,
                        selectedTextColor = textOrange,
                        unselectedTextColor = textColor
                    )
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = buttonColor,
                onClick = {
                    if(catalogoSeleccionado == "animales"){
                        razaSeleccionada = null
                        animalSeleccionado = null
                        mostrarModal = true
                    }else{
                        razaSeleccionada = Raza(0, "", "visible", 0, "")
                        animalSeleccionado = null
                        mostrarModal = true
                    }
                }
            ){
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if(catalogoSeleccionado == "animales"){
                pantallaAnimal(
                    viewModel = viewModel,
                    abrirEditar = { raza, animal ->
                        razaSeleccionada = raza
                        animalSeleccionado = animal
                        mostrarModalEditar = true
                    }
                )
            }else{
                pantallaRaza(
                    viewModel = viewModel,
                    abrirEditar = { raza, animal ->
                        razaSeleccionada = raza
                        animalSeleccionado = animal
                        mostrarModalEditar = true
                    }
                )
            }

            if(mostrarModal){
                modal(
                    onDismissRequest = { mostrarModal = false},
                    raza = razaSeleccionada,
                    viewModel = viewModel,
                    onConfirmation = { nombre, idAnimal ->
                        if(razaSeleccionada == null){
                            viewModel.crearAnimal(nombre)
                        }else{
                            viewModel.crearRazas(
                                Raza(
                                    0, nombre, "visible", idAnimal ?: 0, ""
                                )
                            )
                        }
                        mostrarModal = false
                    }
                )
            }

            if(mostrarModalEditar){
                modaleditar(
                    onDismissRequest = { mostrarModalEditar = false},
                    raza = razaSeleccionada,
                    viewModel = viewModel,
                    onConfirmation = { nombre, idAnimal ->
                        if(razaSeleccionada != null){
                            viewModel.modificarRaza(
                                razaSeleccionada!!.copy(nombre = nombre, animal_id = idAnimal ?: 0)
                            )
                        }else if (animalSeleccionado != null){
                            viewModel.modificarAnimal(
                                animalSeleccionado!!.copy(nombre = nombre)
                            )
                        }
                        mostrarModalEditar = false
                    }
                )
            }
        }
    }
}

