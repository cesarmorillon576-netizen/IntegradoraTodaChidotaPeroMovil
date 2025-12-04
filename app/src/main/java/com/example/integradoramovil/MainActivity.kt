package com.example.integradoramovil

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.integradoramovil.componentes.modal
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.pantallas.*
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.IntegradoraMovilTheme
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textOrange
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntegradoraMovilTheme {
                val viewModel: AnimalRazaUserViewModel = viewModel()
                var mostrarModal by remember { mutableStateOf(false) }
                var mostrarModalEditar by remember {mutableStateOf(false)}
                var razaSeleccionada by remember {mutableStateOf<Raza?>(null)}
                var animalSeleccionado by remember {mutableStateOf<Animal?>(null)}

                Box(
                    modifier = Modifier.fillMaxSize().statusBarsPadding().imePadding()
                ){
                    MAIN(viewModel,
                        mostrarModal = mostrarModal,
                        abrirModal = { laRaza ->
                            razaSeleccionada = laRaza
                            animalSeleccionado = null
                            mostrarModal = true
                                     },

                        cerrarModal = {mostrarModal = false}
                    )

                    if(mostrarModal){
                        modal(
                            onDismissRequest = {
                                mostrarModal = false
                            },
                            raza = razaSeleccionada,
                            viewModel = viewModel,
                            onConfirmation = { nombre, idAnimal ->
                                if(razaSeleccionada == null){
                                    viewModel.viewModelScope.launch{
                                        viewModel.crearAnimal(
                                            Animal(
                                                id_animal = 0,
                                                nombre = nombre,
                                                visibilidad = "visible"
                                            )
                                        )
                                    }
                                }else{
                                    viewModel.viewModelScope.launch {
                                        viewModel.crearRazas(
                                            Raza(
                                                0,
                                                nombre = nombre,
                                                "visible",
                                                id_animal = idAnimal,
                                                ""
                                            )
                                        )
                                    }
                                }
                                mostrarModal = false
                            }
                        )
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MAIN(
        viewModel: AnimalRazaUserViewModel,
        mostrarModal: Boolean, abrirModal: (Raza?) -> Unit,
        cerrarModal: () -> Unit

    ) {

        val navController = rememberNavController()
        val navBackStack = navController.currentBackStackEntryAsState()
        val rutaActual = navBackStack.value?.destination?.route

        val barras = rutaActual != "loginfirst" && rutaActual != "loginsecond"
        Log.e( "estado", "${barras}")
        Scaffold(
            topBar = {
                if(barras){
                    TopAppBar(
                        title = {
                            Text(
                                when(rutaActual){
                                    "animales" -> "Animales"
                                    "razas" -> "Razas"
                                    else -> ""
                                },
                                color = textColor
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = textColorsubMain
                        )
                    )
                }
            },
            bottomBar = {
                if(barras){
                    NavigationBar(
                        containerColor = textColorsubMain
                    ) {
                        NavigationBarItem(
                            selected = rutaActual == "animales",
                            onClick = {
                                navController.navigate("animales")
                            },
                            icon = {},
                            label = {
                                Text("Animales")
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = textOrange,
                                selectedTextColor =  textOrange,
                                unselectedTextColor = textColor
                            ),
                        )

                        NavigationBarItem(
                            selected = rutaActual == "razas",
                            onClick = {
                                navController.navigate("razas")
                            },
                            icon = {},
                            label = {
                                Text("Razas")
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = textOrange,
                                selectedTextColor =  textOrange,
                                unselectedTextColor = textColor
                            )
                        )

                    }
                }
            },
            floatingActionButton = {
                if(barras && (rutaActual == "animales" || rutaActual == "razas")){
                    FloatingActionButton(
                        containerColor = textColorsubMain,
                        onClick = {
                            if(rutaActual == "animales"){
                                abrirModal(null)
                            }else{
                                abrirModal(
                                    Raza(
                                        0,"","visible",0, ""
                                    )
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = com.example.integradoramovil.R.drawable.agregar),
                            tint = textColor,
                            contentDescription = "agregar"
                        )
                    }
                }
            }
        ) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = "loginfirst",
            ){
                composable("loginfirst"){
                    loginFirst(navController)
                }

                composable("loginsecond") {
                    LoginSecond(navController)
                }

                composable("razas"){
                    pantallaRaza(navController, viewModel)
                }

                composable("animales"){
                    pantallaAnimal(navController, viewModel)
                }



            }
        }



    }





}

