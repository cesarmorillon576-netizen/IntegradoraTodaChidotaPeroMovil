package com.example.integradoramovil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import com.example.integradoramovil.network.RetroFitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class AnimalRazaUserViewModel : ViewModel() {

    private val _animales = MutableStateFlow<List<Animal>>(emptyList())
    val animales: StateFlow<List<Animal>> = _animales.asStateFlow()

    private val _razas = MutableStateFlow<List<Raza>>(emptyList())
    val razas: StateFlow<List<Raza>> = _razas.asStateFlow()

    init {
        cargarRazas()
        cargarAnimales()
    }

    // funciones de razas
    fun cargarRazas() {
        viewModelScope.launch {
            try {
                val res = RetroFitClient.api.obtenerRazas()
                _razas.value = res
            } catch (e: Exception) {
                // Manejo de errores (luego veo)
            }
        }
    }

    fun modificarRaza(raza: Raza) {
        viewModelScope.launch {
            try {
                RetroFitClient.api.actualizarRaza(raza.id_raza, raza.nombre, raza.id_animal)
                cargarRazas()
            } catch (e: Exception) {
                // Manejo de erroressss
            }
        }
    }

    fun eliminarRaza(raza: Raza) {
        viewModelScope.launch {
            try {
                RetroFitClient.api.eliminarRaza(raza.id_raza)
                cargarRazas()
            } catch (e: Exception) {
                // los errores
            }
        }
    }

    fun crearRazas(raza: Raza) {
        viewModelScope.launch {
            try {
                val nuevaRaza = RazaRequest(raza.nombre, raza.id_animal)
                RetroFitClient.api.crearRaza(nuevaRaza)
                cargarRazas()

            } catch (e: Exception) {

            }
        }
    }


    // Funciones de animales
    fun cargarAnimales() {
        viewModelScope.launch {
            try {
                val res = RetroFitClient.api.obtenerAnimales()
                _animales.value = res
            } catch (e: Exception) {
                // errorsillos
            }
        }
    }

    fun modificarAnimal(animal: Animal) {
        viewModelScope.launch {
            try {
                RetroFitClient.api.actualizarAnimal(animal.id_animal, animal.nombre)
                cargarAnimales()
            } catch (e: Exception) {
                // erroresssssssssssssssssssssssssssss
            }
        }
    }

    fun eliminarAnimal(animal: Animal) {
        viewModelScope.launch {
            try {
                RetroFitClient.api.borrarAnimal(animal.id_animal)
                cargarAnimales()
            } catch (e: Exception) {
                // errores con e
            }
        }
    }

    fun crearAnimal(animal: Animal) {
        viewModelScope.launch {
            try {
                RetroFitClient.api.crearAnimal(animal.nombre)
                cargarAnimales()
            } catch (e: Exception) {
                // erroresssssssssssssssssssssssssssss
            }
        }
    }

    fun cambiarEstado(animal: Animal?, raza: Raza?) {
        viewModelScope.launch {
            try {
                if (animal != null) {
                    RetroFitClient.api.cambiarEstadoAnimal(animal.id_animal)
                } else if (raza != null) {
                    RetroFitClient.api.cambiarEstadoRaza(raza.id_raza)
                } else {
                    throw Exception("Animal o raza no pueden ser nulos")
                }
            } catch (e: Exception) {
                // erroresssssssssssss
            }

        }
    }
}