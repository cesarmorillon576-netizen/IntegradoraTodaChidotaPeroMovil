package com.example.integradoramovil.modelos
import android.content.Context
import com.google.gson.Gson

object AuthManager {
    private val gson = Gson()
    private const val PREFS_NAME = "auth_prefs"

    var user: User? = null
    var token: String? = null

    fun login(loginResponse: LoginResponse?, context: Context){
        this.user = loginResponse?.user
        this.token = loginResponse?.token

        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putString("token", token)

        val userJson = gson.toJson(user)
        editor.putString("user", userJson)

        editor.apply()
    }

    fun logout(context: Context){
        this.user = null
        this.token = null
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun init(context: Context){
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        token = prefs.getString("token", null)
        val userJson = prefs.getString("user", null)
        if(userJson != null){
            user = gson.fromJson(userJson, User::class.java)
        }
    }

    fun getToken(context: Context): String?{
        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return prefs.getString("token", null)
    }

    fun isAdmin(): Boolean = user?.rol?.nombre == "administrador"
}