package com.example.integradoramovil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.Usuario
import com.example.integradoramovil.network.RetroFitClient

class AnimalRazaUserViewModel: ViewModel() {
    val user = mutableStateOf<Usuario?>(null)

    val animales = mutableStateOf<List<Animal>>(emptyList())

    val razas = mutableStateOf<List<Raza>>(emptyList())

    // funciones de usuario (login/logout)


    // funciones de razas


    // Funciones de animales
    suspend fun cargarAnimales(): Boolean{
        return try{
            val res = RetroFitClient.api.obtenerAnimales()
            animales.value = res
            true
        }catch (e: Exception){
            println(e.message)
            false
        }
    }

    suspend fun modificarAnimal(animal: Animal): Boolean{
        return try{
            RetroFitClient.api.actualizarAnimal(animal.nombre)
            cargarAnimales()
            true
        }catch(e: Exception){
            println(e.message)
            false
        }

    }

    suspend fun eliminarAnimal(animal: Animal): Boolean{
        return try {
            RetroFitClient.api.borrarAnimal(animal.id_animal)
            cargarAnimales()
            true
        }catch (e: Exception){
            println(e.message)
            false
        }
    }

    suspend fun crearAnimal(animal: Animal): Boolean{
        return try {
            RetroFitClient.api.crearAnimal(animal.nombre)
            cargarAnimales()
            true
        }catch(e: Exception){
            println(e.message)
            false
        }

    }

    suspend fun cambiarEstado(animal: Animal): Boolean{
        return try{
            RetroFitClient.api.cambiarEstadoAnimal(animal.id_animal)
            true
        }catch(e: Exception){
            println(e.message)
            false
        }
    }
}