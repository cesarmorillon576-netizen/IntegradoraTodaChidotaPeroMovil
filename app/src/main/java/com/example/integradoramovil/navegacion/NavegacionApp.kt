package com.example.integradoramovil.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.integradoramovil.pantallas.Catalogos
import com.example.integradoramovil.pantallas.Citas
import com.example.integradoramovil.pantallas.Home
import com.example.integradoramovil.pantallas.Mascotas
import com.example.integradoramovil.pantallas.LoginSecond
import com.example.integradoramovil.pantallas.loginFirst

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "loginfirst"
    ) {
        composable("loginfirst") { loginFirst(navController) }
        composable("loginsecond") { LoginSecond(navController) }

        composable("home") { Home(navController) }
        composable("citas") { Citas(navController) }
        composable("catalogos") { Catalogos(navController) }
        composable("mascotas") { Mascotas(navController) }
    }
}