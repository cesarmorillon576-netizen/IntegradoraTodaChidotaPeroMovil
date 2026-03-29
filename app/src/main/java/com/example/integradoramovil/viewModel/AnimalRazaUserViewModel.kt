package com.example.integradoramovil.viewModel

import androidx.compose.ui.platform.LocalContext
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
        cargarRazas()
        cargarAnimales()
    }

    // ========== FUNCIONES DE RAZAS ==========

    fun cargarRazas() {
        viewModelScope.launch {
            repository.cargarRazas()
        }
    }

    fun modificarRaza(raza: Raza) {
        viewModelScope.launch {
            repository.modificarRaza(raza)
        }
    }

    fun eliminarRaza(raza: Raza) {
        viewModelScope.launch {
            repository.eliminarRaza(raza.id_raza)
        }
    }

    fun crearRazas(raza: Raza) {
        viewModelScope.launch {
            repository.crearRaza(raza)
        }
    }

    // ========== FUNCIONES DE ANIMALES ==========

    fun cargarAnimales() {
        viewModelScope.launch {
            repository.cargarAnimales()
        }
    }

    fun modificarAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.modificarAnimal(animal)
        }
    }

    fun eliminarAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.eliminarAnimal(animal.id_animal)
        }
    }

    fun crearAnimal(animal: Animal) {
        viewModelScope.launch {
            repository.crearAnimal(animal.nombre)
        }
    }

    fun cambiarEstado(animal: Animal?, raza: Raza?) {
        viewModelScope.launch {
            if (animal != null) {
                repository.cambiarEstadoAnimal(animal.id_animal)
            } else if (raza != null) {
                repository.cambiarEstadoRaza(raza.id_raza)
            }
        }
    }


fun clearError() {
        repository.clearError()
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


