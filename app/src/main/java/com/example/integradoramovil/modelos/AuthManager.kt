package com.example.integradoramovil.modelos
import android.content.Context


object AuthManager {
    private var rolUsuario: String = "usuario"
    private var usuarioId: Int? = null
    private var token: String? = null

    fun login(rolUsuario: String, usuarioId: Int?, token: String?, context: Context){
        this.rolUsuario = rolUsuario
        this.usuarioId = usuarioId
        this.token = token

        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        prefs.edit().putString("rol", rolUsuario).apply()
        prefs.edit().putString("token", token).apply()
    }

    fun logout(context: Context){
        this.rolUsuario = "usuario"
        usuarioId = null
        token = null
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun getToken(contexet: Context): String?{
        val prefs = contexet.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    fun getRol(context: Context): String{
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return prefs.getString("rol", "usuario") ?: "usuario"
    }

}