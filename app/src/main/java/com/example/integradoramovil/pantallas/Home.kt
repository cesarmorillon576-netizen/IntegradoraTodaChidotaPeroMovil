package com.example.integradoramovil.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integradoramovil.componentes.MenuCard
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.R
import com.example.integradoramovil.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val context = LocalContext.current
    val user = AuthManager.user
    val nombre = user?.nombre ?: "Usuario"

    var lastClickTime by remember { mutableLongStateOf(0L) }
    val safeNavigate: (String) -> Unit = { route ->
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > 500L) {
            navController.navigate(route)
            lastClickTime = currentTime
        }
    }

    Scaffold(
        containerColor = Background,
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = BackgroundCard,
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 24.dp)
                ) {
                    Text(
                        text = "¡Hola,",
                        fontSize = 18.sp,
                        color = TextSub,
                        fontFamily = baloo
                    )
                    Text(
                        text = "${nombre.replaceFirstChar { it.uppercase() }}!",
                        fontSize = 32.sp,
                        color = TextMain,
                        fontWeight = FontWeight.Black,
                        fontFamily = baloo,
                        lineHeight = 36.sp
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Background
            ) {
                OutlinedButton(
                    onClick = {
                        AuthManager.logout(context)
                        navController.navigate("loginfirst") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRed.copy(alpha = 0.3f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión", fontWeight = FontWeight.Bold, fontFamily = baloo)
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Gestión de mascotas",
                    color = TextMain,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = baloo,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }

            item {
                MenuCard(
                    titulo = if(!AuthManager.hasPerms()) "Mis Mascotas" else "Mascotas",
                    subtitulo = if(!AuthManager.hasPerms()) "Gestiona y visualiza tus mascotas" else "Listado de mascotas",
                    iconRes = R.drawable.huella,
                    onClick = { safeNavigate("mascotas") }
                )
            }

            item {
                MenuCard(
                    titulo = "Citas",
                    subtitulo = when{
                      AuthManager.isAdmin() -> "Gestión de agenda"
                      AuthManager.isTrabajador() -> "Citas asignadas"
                      else -> "¡Consulta tus próximas visitas!"
                    },
                    iconRes = R.drawable.agregar,
                    onClick = { safeNavigate("citas") }
                )
            }

            if (AuthManager.hasPerms()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Administración",
                        color = TextMain,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = baloo
                    )
                }
                item {
                    MenuCard(
                        titulo = "Catálogos",
                        subtitulo = if(AuthManager.isAdmin()) "Gestión de animales y razas" else "Consulta de catálogos",
                        iconRes = R.drawable.huella,
                        onClick = { safeNavigate("catalogos") }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}