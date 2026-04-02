package com.example.integradoramovil.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.network.RetroFitClient
import com.example.integradoramovil.repositorios.AnimalRazaRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimalRazaUserViewModel(
    private val repository: AnimalRazaRepository
) : ViewModel() {
    // Exponer StateFlows del Repository
    val animales: StateFlow<List<Animal>> = repository.animales
    val razas: StateFlow<List<Raza>> = repository.razas
    val isLoading: StateFlow<Boolean> = repository.isLoading
    val error: StateFlow<String?> = repository.error

    init {
        fullLoad()
    }

    fun fullLoad(){
        viewModelScope.launch {
            repository.cargarRazas()
            repository.cargarAnimales()
        }
    }

    // ========== FUNCIONES DE RAZAS ==========

    fun cargarRazas() {
        if (repository.razas.value.isNotEmpty() || repository.isLoading.value) return
        viewModelScope.launch {
            repository.cargarRazas()
        }
    }

    fun modificarRaza(raza: Raza) {
        viewModelScope.launch {
            repository.modificarRaza(raza)
            cargarRazas()
        }
    }

    fun eliminarRaza(raza: Raza) {
        viewModelScope.launch {
            repository.eliminarRaza(raza.id)
            cargarRazas()
        }
    }

    fun crearRazas(raza: Raza) {
        viewModelScope.launch {
            repository.crearRaza(raza)
            cargarRazas()
        }
    }

    // ========== FUNCIONES DE ANIMALES ==========

    fun cargarAnimales() {
        if (repository.animales.value.isNotEmpty() || repository.isLoading.value) return
        viewModelScope.launch {
            repository.cargarAnimales()
        }
    }

    fun modificarAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.modificarAnimal(animal)
            cargarAnimales()
        }
    }

    fun eliminarAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.eliminarAnimal(animal.id)
            cargarAnimales()
        }
    }

    fun crearAnimal(animal: String, visibilidad: String? = "visible") {
        viewModelScope.launch {
            repository.crearAnimal(animal, visibilidad)
            cargarAnimales()
        }
    }

    fun cambiarEstado(animal: Animal?, raza: Raza?) {
        viewModelScope.launch {
            if (animal != null) {
                repository.cambiarEstadoAnimal(animal.id)
            } else if (raza != null) {
                repository.cambiarEstadoRaza(raza.id)
            }
        }
    }

    companion object Factory {
        fun create(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val api = RetroFitClient.getApi(context)
                    val repository = AnimalRazaRepository(api)
                    return AnimalRazaUserViewModel(repository) as T
                }
            }
        }
    }
}


