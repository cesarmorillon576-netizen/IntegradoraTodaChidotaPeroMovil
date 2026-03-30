package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.integradoramovil.componentes.CitaCard
import com.example.integradoramovil.componentes.Pagination
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textOrange
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
    val paginaActual: Int by viewModel.paginaActual.collectAsState()
    val ultimaPagina: Int by viewModel.ultimaPagina.collectAsState()

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text("Gestión de Citas", color = textColor, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = textColorsubMain)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.weight(1f)) {
                if (cargando && listaCitas.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = textOrange
                    )
                } else if(listaCitas.isEmpty() && !cargando) {
                    Text("No hay citas disponibles", modifier = Modifier.align(Alignment.Center))
                }else{
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(listaCitas) { cita ->
                            CitaCard(cita)
                        }
                    }
                }
            }

            if (ultimaPagina > 0) {
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