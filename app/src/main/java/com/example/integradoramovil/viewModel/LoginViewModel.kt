package com.example.integradoramovil.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.integradoramovil.network.RetroFitClient
import com.example.integradoramovil.repositorios.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {
    
    // Expose repository StateFlows
    val isLoading = repository.isLoading
    val loginError = repository.loginError
    val emailValidationError = repository.emailValidationError
    val passwordValidationError = repository.passwordValidationError

    fun validateEmail(email: String) {
        repository.validateEmail(email)
    }

    fun validatePassword(password: String) {
        repository.validatePassword(password)
    }

    fun login(email: String, password: String, navController: NavController, context: Context) {
        viewModelScope.launch {
            val success = repository.login(email, password, context)
            if (success) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    fun clearLoginError() {
        repository.clearLoginError()
    }

    companion object Factory {
        fun create(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val api = RetroFitClient.getApi(context)
                    val repository = LoginRepository(api)
                    return LoginViewModel(repository) as T
                }
            }
        }
    }
}
