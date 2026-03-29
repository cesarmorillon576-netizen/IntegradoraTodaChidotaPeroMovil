package com.example.integradoramovil.repositorios

import android.content.Context
import com.example.integradoramovil.modelos.ApiResponse
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.modelos.TokenData
import com.example.integradoramovil.network.apiservice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Response

class LoginRepository(private val api: apiservice) {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    private val _emailValidationError = MutableStateFlow<String?>(null)
    val emailValidationError: StateFlow<String?> = _emailValidationError.asStateFlow()

    private val _passwordValidationError = MutableStateFlow<String?>(null)
    val passwordValidationError: StateFlow<String?> = _passwordValidationError.asStateFlow()

    suspend fun login(email: String, password: String, context: Context): Boolean {
        clearValidationErrors()
        val isValid = validateCredentials(email, password)
        if (!isValid) return false

        _isLoading.value = true
        _loginError.value = null

        return try {
            val response = api.login(email, password)
            if (response.isSuccessful) {
                val body = response.body()
                val token = body?.data?.token
                val mensaje = body?.mensaje

                if (token != null) {
                    AuthManager.login("administrador", null, token, context)
                    true
                } else {
                    _loginError.value = mensaje ?: "Error desconocido"
                    false
                }
            } else {
                val errorJson = response.errorBody()?.string()
                val gson = com.google.gson.Gson()
                val errorResponse = gson.fromJson(errorJson, ApiResponse::class.java)
                _loginError.value = errorResponse?.mensaje ?: "Error del servidor"
                false
            }
        } catch (e: Exception) {
            _loginError.value = "Error de conexión: ${e.message}"
            false
        } finally {
            _isLoading.value = false
        }
    }

    fun validateEmail(email: String) {
        if (email.isEmpty()) {
            _emailValidationError.value = "Campo requerido"
        } else {
            val numeroRegex = Regex("^[0-9]{10}$")
            val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z]{2,}\\.[a-zA-Z]{2,}$")
            
            if (numeroRegex.matches(email)) {
                _emailValidationError.value = ""
            } else if (email.all { it.isDigit() }) {
                _emailValidationError.value = "Número no válido"
            } else if (emailRegex.matches(email)) {
                _emailValidationError.value = ""
            } else {
                _emailValidationError.value = "Correo no válido"
            }
        }
    }

    fun validatePassword(password: String) {
        if (password.isEmpty()) {
            _passwordValidationError.value = "Campo requerido"
        } else if (password.length < 8) {
            _passwordValidationError.value = "Mínimo 8 caracteres"
        } else {
            _passwordValidationError.value = ""
        }
    }

    fun clearValidationErrors() {
        _emailValidationError.value = null
        _passwordValidationError.value = null
    }

    fun clearLoginError() {
        _loginError.value = null
    }
}
