package com.example.integradoramovil.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.network.RetroFitClient
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.integradoramovil.network.apiservice
import kotlinx.coroutines.launch
import retrofit2.*


object LoginViewModel : ViewModel() {
    var isNotEmail by mutableStateOf("")
    var isNotPassword by mutableStateOf("")
    var IsText by mutableStateOf("")
    var IsLoading by mutableStateOf(false)

    fun trying(correo:String, password:String, navController: NavController){
        if(isCorrect(correo,password)==0){
            Log.e("trying","Validación correcta, llamando a LoginApi otra vez")
            LoginApi(correo,password, navController)
            IsLoading=true
        }else{
            IsLoading=false
            Log.e("trying","Validación fallida, no se llama LoginApi")
        }

    }


    fun isCorrect(correo:String,password:String): Int{


        // var map = mutableStateOf<Map<String,String>>(mapOf())
        var isCorrectV = mutableStateOf(false)
        var isCorrectInt = mutableStateOf(0)
        var isNotValid = mutableStateOf(2)

        val RxCorreo = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val RxNumero = Regex("^[0-9]{10}$")
        val RxContraseña = Regex("^.{8,}$")

        if (correo.isEmpty()) {
            isNotEmail = "Input vacío"
            isCorrectInt.value += 1
        } else if (RxNumero.matches(correo)) {
            isNotEmail = ""
        } else if (correo.all { it.isDigit() }) {
            isNotEmail = "El número no es válido"
            isCorrectInt.value += 1
        } else if (RxCorreo.matches(correo)) {
            isNotEmail = ""
        } else {
            isNotEmail = "El correo no es válido"
            isCorrectInt.value += 1
        }

        if(password.isNullOrEmpty()){
            isNotPassword = "input vacio"
            isCorrectInt.value+=1
        }else if(!RxContraseña.containsMatchIn(password)){
            isNotPassword = "La longitud de la contraseña debe ser de 8 caracteres"
            isCorrectInt.value+=1
        }else{
            isNotPassword =""
        }
        Log.e("response","${isCorrectV.value}")
        return isCorrectInt.value
    }

    fun LoginApi(correo:String, password:String, navController: NavController){
        Log.e("response","entro 1")
        viewModelScope.launch {
            Log.e("response","entro")
            try {
                val response = RetroFitClient.api.login(correo, password)
                if (response.isSuccessful) {
                    val data = response.body()
                    if(data?.user?.id_rol ==1){
                        Log.e("respones","${data}")
                        IsText=""
                        navController.navigate("animales")
                    }else{
                        Log.e("respones","${data}")
                        IsText="La contraseña o el numero/o correo son incorrectos"
                    }
                } else {
                    IsText="La contraseña o el numero/o correo son incorrectos"
                    Log.e("response", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                IsText="Error con la conexion"
                Log.e("response", "Exception: ${e.message}")
            }finally {
                IsLoading=false
            }
        }
    }
}