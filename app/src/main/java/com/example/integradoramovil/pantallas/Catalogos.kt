package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.modal
import com.example.integradoramovil.componentes.modaleditar
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.*
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Catalogos(navController: NavController) {
    val context = LocalContext.current
    val viewModel: AnimalRazaUserViewModel = viewModel(
        factory = AnimalRazaUserViewModel.create(context)
    )

    // estados del viumodel
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val animales by viewModel.animales.collectAsState()
    val razas by viewModel.razas.collectAsState()

    // estados de la pantalla
    var catalogoSeleccionado by remember { mutableStateOf("animales") }
    var mostrarModal by remember { mutableStateOf(false) }
    var mostrarModalEditar by remember { mutableStateOf(false) }
    var razaSeleccionada by remember { mutableStateOf<Raza?>(null) }
    var animalSeleccionado by remember { mutableStateOf<Animal?>(null) }

    Scaffold(
        containerColor = Background,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        if (!navController.popBackStack()) {
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = PrimaryOrange)
                    }
                },
                title = {
                    Text(
                        text = if (catalogoSeleccionado == "animales") "Catálogo de Animales" else "Catálogo de Razas",
                        color = TextMain,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = baloo,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundCard)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = BackgroundCard,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = catalogoSeleccionado == "animales",
                    onClick = { catalogoSeleccionado = "animales" },
                    label = { Text("Animales", fontWeight = FontWeight.Bold, fontFamily = baloo) },
                    icon = { Icon(Icons.Default.Pets, contentDescription = null) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = PrimaryOrange.copy(alpha = 0.2f),
                        selectedIconColor = PrimaryOrange,
                        selectedTextColor = PrimaryOrange,
                        unselectedIconColor = TextSub,
                        unselectedTextColor = TextSub
                    )
                )
                NavigationBarItem(
                    selected = catalogoSeleccionado == "razas",
                    onClick = { catalogoSeleccionado = "razas" },
                    label = { Text("Razas", fontWeight = FontWeight.Bold, fontFamily = baloo) },
                    icon = { Icon(Icons.Default.Category, contentDescription = null) },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = PrimaryOrange.copy(alpha = 0.2f),
                        selectedIconColor = PrimaryOrange,
                        selectedTextColor = PrimaryOrange,
                        unselectedIconColor = TextSub,
                        unselectedTextColor = TextSub
                    )
                )
            }
        },
        floatingActionButton = {
            if(AuthManager.isAdmin()){
                FloatingActionButton(
                    containerColor = PrimaryOrange,
                    contentColor = Color.White,
                    shape = CircleShape,
                    onClick = {
                        animalSeleccionado = null
                        razaSeleccionada = if (catalogoSeleccionado == "razas") {
                            Raza(0, "", "visible", 0, null)
                        } else {
                            null
                        }
                        mostrarModal = true
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(30.dp))
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading && (animales.isEmpty() && razas.isEmpty()) -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = PrimaryOrange
                    )
                }

                error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Hubo un problema al cargar los datos", color = TextMain, fontWeight = FontWeight.Bold, fontFamily = baloo)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.fullLoad() },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                        ) {
                            Text("Reintentar", fontFamily = baloo)
                        }
                    }
                }

                else -> {
                    if (catalogoSeleccionado == "animales") {
                        pantallaAnimal(
                            viewModel = viewModel,
                            abrirEditar = { _, animal ->
                                razaSeleccionada = null
                                animalSeleccionado = animal
                                mostrarModalEditar = true
                            }
                        )
                    } else {
                        pantallaRaza(
                            viewModel = viewModel,
                            abrirEditar = { raza, _ ->
                                razaSeleccionada = raza
                                animalSeleccionado = null
                                mostrarModalEditar = true
                            }
                        )
                    }
                }
            }

            // modalillos
            if (mostrarModal) {
                modal(
                    onDismissRequest = { mostrarModal = false },
                    raza = razaSeleccionada,
                    viewModel = viewModel,
                    onConfirmation = { nombre, idAnimal, visibilidad ->
                        if (catalogoSeleccionado == "animales") {
                            viewModel.crearAnimal(nombre, visibilidad)
                        } else {
                            viewModel.crearRazas(Raza(0, nombre, visibilidad, idAnimal ?: 0,))
                        }
                        mostrarModal = false
                    }
                )
            }

            if (mostrarModalEditar) {
                modaleditar(
                    onDismissRequest = { mostrarModalEditar = false },
                    raza = razaSeleccionada,
                    animal = animalSeleccionado,
                    viewModel = viewModel,
                    onConfirmation = { nombre, idAnimal ->
                        if (razaSeleccionada != null) {
                            viewModel.modificarRaza(razaSeleccionada!!.copy(nombre = nombre, animal_id = idAnimal ?: 0))
                        } else if (animalSeleccionado != null) {
                            viewModel.modificarAnimal(animalSeleccionado!!.copy(nombre = nombre))
                        }
                        mostrarModalEditar = false
                    }
                )
            }
        }
    }
}