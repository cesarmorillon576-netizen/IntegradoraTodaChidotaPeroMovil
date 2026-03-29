package com.example.integradoramovil.navegacion

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.pantallas.Catalogos
import com.example.integradoramovil.pantallas.Citas
import com.example.integradoramovil.pantallas.Home
import com.example.integradoramovil.pantallas.Mascotas
import com.example.integradoramovil.pantallas.LoginSecond
import com.example.integradoramovil.pantallas.loginFirst

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // val context = LocalContext.current
    val inicio = if (AuthManager.token != null) "home" else "loginfirst"
    NavHost(
        navController = navController,
        startDestination = inicio
    ) {
        // composables de login
        composable("loginfirst") { loginFirst(navController) }
        composable("loginsecond") { LoginSecond(navController) }

        // pantallas principales
        composable("home") { Home(navController) }
        composable("citas") { Citas(navController) }
        composable("catalogos") { Catalogos(navController) }
        composable("mascotas") { Mascotas(navController) }
    }
}