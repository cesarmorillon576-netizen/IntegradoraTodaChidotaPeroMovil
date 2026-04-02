package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.integradoramovil.R
import com.example.integradoramovil.componentes.MascotaCard
import com.example.integradoramovil.componentes.MascotaInfo
import com.example.integradoramovil.componentes.Pagination
import com.example.integradoramovil.componentes.modal
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.ui.theme.*
import com.example.integradoramovil.viewModel.MascotaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Mascotas(navController: NavController) {
    val context = LocalContext.current
    val viewModel: MascotaViewModel = viewModel(factory = MascotaViewModel.create(context))

    val mascotas by viewModel.mascotas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val infoPaginacion by viewModel.paginacion.collectAsState()

    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    var mostrarDetalles by remember { mutableStateOf(false) }
    var mostrarModal by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if(AuthManager.hasPerms()) "Gestión de Mascotas" else "Mis Mascotas",
                        fontSize = 22.sp,
                        color = TextMain,
                        fontFamily = baloo,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if(!navController.popBackStack()){
                            navController.navigate("home"){
                                popUpTo("home"){inclusive = true}
                            }
                        }

                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = PrimaryOrange)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BackgroundCard)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mascotaSeleccionada = null; mostrarModal = true },
                containerColor = PrimaryOrange,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(28.dp))
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                isLoading && mascotas.isEmpty() -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = PrimaryOrange)
                }

                // error
                error != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Algo salió mal al conectar", color = TextMain, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.fullLoad() }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)) {
                            Text("Reintentar")
                        }
                    }
                }

                mascotas.isEmpty() -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.gato),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp),
                            tint = PrimaryOrange.copy(alpha = 0.4f)
                        )
                        Text("No tienes mascotas registradas", fontFamily = baloo, color = TextSub, fontSize = 18.sp)
                    }
                }


                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(
                                start = 16.dp,
                                end = 16.dp,
                                top = 16.dp,
                                bottom = 80.dp
                            )
                        ) {
                            items(mascotas) { mascota ->
                                MascotaCard(
                                    mascota = mascota,
                                    onClick = { mascotaSeleccionada = mascota; mostrarDetalles = true },
                                    onEdit = { mascotaSeleccionada = mascota; mostrarModal = true },
                                    onDelete = { viewModel.eliminarMasota(mascota.id) }
                                )
                            }
                        }

                        // paginacion
                        infoPaginacion?.let { pag ->
                            if (pag.last_page > 1) {
                                Box(modifier = Modifier.padding(bottom = 16.dp)){
                                    Pagination(
                                        actual = pag.current_page,
                                        total = pag.last_page,
                                        onPageChange = { nuevaPagina ->
                                            viewModel.cambiarPagina(nuevaPagina)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // info y modal
            if(mostrarDetalles && mascotaSeleccionada != null) {
                MascotaInfo(mascota = mascotaSeleccionada!!, onDismissRequest = { mostrarDetalles = false })
            }

            if(mostrarModal) {
                modal(
                    onDismissRequest = { mostrarModal = false; mascotaSeleccionada = null },
                    mascota = mascotaSeleccionada,
                    viewModel = viewModel,
                    onConfirmation = { request ->
                        if(mascotaSeleccionada == null) viewModel.GuardarMascota(request)
                        else viewModel.editarMascota(request, mascotaSeleccionada!!.id)
                        mostrarModal = false
                    }
                )
            }
        }
    }
}