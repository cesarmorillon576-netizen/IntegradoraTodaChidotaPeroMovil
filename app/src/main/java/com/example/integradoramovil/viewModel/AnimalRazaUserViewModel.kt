package com.example.integradoramovil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import com.example.integradoramovil.network.RetroFitClient

class AnimalRazaUserViewModel: ViewModel() {

    val animales = mutableStateOf<List<Animal>>(emptyList())

    val razas = mutableStateOf<List<Raza>>(emptyList())

    // funciones de razas
    suspend fun cargarRazas(): Boolean {
        return try {
            val res = RetroFitClient.api.obtenerRazas()
            razas.value = res
            println(res)
            true
        } catch(e: Exception) {
            println(e.message)
            false
        }
    }

    suspend fun modificarRaza(raza: Raza): Boolean {
        return try{
            RetroFitClient.api.actualizarRaza(raza.nombre, raza.id_animal)
            cargarRazas()
            true
        }catch(e: Exception){
            println(e.message)
            false
        }
    }

    suspend fun eliminarRazr(raza: Raza): Boolean{
        return try {
            RetroFitClient.api.eliminarRaza(raza.id_raza)
            cargarAnimales()
            true
        }catch (e: Exception){
            println(e.message)
            false
        }
    }

    suspend fun crearRazas(raza: Raza): Boolean{
        return try {
            val nuevaRaza = RazaRequest(raza.nombre, raza.id_animal)
            val res = RetroFitClient.api.crearRaza(nuevaRaza)
            println(res)
            cargarRazas()
            true
        }catch(e: Exception){
            println(e.message)
            false
        }
    }

    suspend fun cambiarEstado(raza: Raza): Boolean{
        return try{
            RetroFitClient.api.cambiarEstadoRaza(raza.id_raza)
            true
        }catch(e: Exception){
            println(e.message)
            false
        }
    }

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
            RetroFitClient.api.actualizarAnimal(
                animal.id_animal,
                animal.nombre
            )
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