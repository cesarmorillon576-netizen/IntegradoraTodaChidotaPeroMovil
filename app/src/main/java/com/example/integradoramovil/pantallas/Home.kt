package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.MenuCard
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain

@ExperimentalMaterial3Api
@Composable
fun Home(navController: NavController){

    val context = LocalContext.current
    val user = AuthManager.user
    val nombre = user?.nombre ?: "Usuario"
    val rolusuario = user?.rol?.nombre ?: "Cliente"
    LaunchedEffect(Unit) {
        android.util.Log.d("DEBUG_CITAS", "Citas recibidas: ${AuthManager.getToken(context )}")
    }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Column(modifier = Modifier.padding(16.dp).statusBarsPadding()) {
                        Text("Bienvenido, ${nombre.capitalize()}",
                            fontSize = 28.sp,
                            color = textColor,
                            fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = textColorsubMain
                )
            )

        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item{
                Text("Kenia", fontSize = 18.sp, color = textColor)
            }
            item{
                MenuCard("Mascotas", "Visualiza tus mascotas") {
                    navController.navigate("mascotas")
                }
            }
            item{
                MenuCard("Agendar Cita",
                    "se te va a morir tu mascota si no nos pagas") {
                    navController.navigate("citas")
                }
            }

            if(AuthManager.isAdmin()){
                item{
                    Text("El administrador administra", fontSize = 18.sp, color = textColor)
                }
                item{
                    MenuCard("Catalogos", "Razas y animales") {
                        navController.navigate("catalogos")
                    }
                }
            }

            item{
                Button(
                    onClick = {
                        AuthManager.logout(context)
                        navController.navigate("loginfirst"){ popUpTo(0)}
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cerrar Sesión")
                }
            }
        }
    }
}