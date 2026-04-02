package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.navigation.NavHostController
import com.example.integradoramovil.componentes.CitaCard
import com.example.integradoramovil.componentes.Pagination
import com.example.integradoramovil.ui.theme.*
import com.example.integradoramovil.viewModel.CitaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Citas(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: CitaViewModel = viewModel(
        factory = CitaViewModel.create(context)
    )

    val listaCitas by viewModel.citas.collectAsState()
    val cargando by viewModel.isLoading.collectAsState()
    val paginaActual by viewModel.paginaActual.collectAsState()
    val ultimaPagina by viewModel.ultimaPagina.collectAsState()

    Scaffold(
        containerColor = Background,
        topBar = {

            CenterAlignedTopAppBar(
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
                title = {
                    Text(
                        "Gestión de Citas",
                        color = TextMain,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = baloo,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BackgroundCard
                )
            )
        },

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                when {
                    cargando && listaCitas.isEmpty() -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = PrimaryOrange
                        )
                    }

                    listaCitas.isEmpty() -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("🗓️", fontSize = 60.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No hay citas programadas",
                                color = TextMain,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                fontFamily = baloo
                            )
                            Text(
                                "Si quieres agendar una cita ve a la aplicacion web",
                                color = TextSub.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    }


                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(listaCitas) { cita ->
                                CitaCard(cita)
                            }

                            item { Spacer(modifier = Modifier.height(16.dp)) }
                        }
                    }
                }


                if (cargando && listaCitas.isNotEmpty()) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter),
                        color = PrimaryOrange,
                        trackColor = Color.Transparent
                    )
                }
            }

            // paginacion
            if (ultimaPagina > 1) {
                Pagination(
                    actual = paginaActual,
                    total = ultimaPagina,
                    onPageChange = { nuevaPagina ->
                        viewModel.cambiarPagina(nuevaPagina)
                    }
                )
            }
        }
    }
}