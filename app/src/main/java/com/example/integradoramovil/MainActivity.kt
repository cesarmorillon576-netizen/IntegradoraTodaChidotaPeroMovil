package com.example.integradoramovil

import android.os.Bundle
import com.example.integradoramovil.navegacion.AppNavigation
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.ui.theme.IntegradoraMovilTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        AuthManager.init(this)
        setContent {
            IntegradoraMovilTheme {
                AppNavigation()
            }
        }
    }
}
