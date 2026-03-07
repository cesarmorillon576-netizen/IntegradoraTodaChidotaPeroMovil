package com.example.integradoramovil.repositorios

import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import com.example.integradoramovil.network.RetroFitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList

class AnimalRazaRepository {

    private val _animales = MutableStateFlow<List<Animal>>(emptyList())
    val animales: StateFlow<List<Animal>> = _animales.asStateFlow()

    private val _razas = MutableStateFlow<List<Raza>>(emptyList())
    val razas: StateFlow<List<Raza>> = _razas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // ========== FUNCIONES DE ANIMALES ==========

    suspend fun cargarAnimales() {
        _isLoading.value = true
        _error.value = null
        try {
            val res = RetroFitClient.api.obtenerAnimales()
            _animales.value = res
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cargar animales"
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun crearAnimal(nombre: String): Result<Animal> {
        _isLoading.value = true
        _error.value = null
        return try {
            val response = RetroFitClient.api.crearAnimal(nombre)
            // Después de crear, recargamos la lista
            cargarAnimales()
            Result.success(Animal(0, nombre, "activo"))
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al crear animal"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun modificarAnimal(animal: Animal): Result<Unit> {
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.actualizarAnimal(animal.id_animal, animal.nombre)
            cargarAnimales()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al modificar animal"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun eliminarAnimal(id: Int): Result<Unit> {
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.borrarAnimal(id)
            cargarAnimales()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al eliminar animal"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun cambiarEstadoAnimal(id: Int): Result<Unit> {
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.cambiarEstadoAnimal(id)
            cargarAnimales()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cambiar estado del animal"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    // ========== FUNCIONES DE RAZAS ==========

    suspend fun cargarRazas() {
        _isLoading.value = true
        _error.value = null
        try {
            val res = RetroFitClient.api.obtenerRazas()
            _razas.value = res
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cargar razas"
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun crearRaza(raza: Raza): Result<Raza> {
        _isLoading.value = true
        _error.value = null
        return try {
            val razaRequest = RazaRequest(raza.nombre, raza.id_animal)
            RetroFitClient.api.crearRaza(razaRequest)
            cargarRazas()
            Result.success(raza)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al crear raza"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun modificarRaza(raza: Raza): Result<Unit> {
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.actualizarRaza(raza.id_raza, raza.nombre, raza.id_animal)
            cargarRazas()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al modificar raza"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun eliminarRaza(id: Int): Result<Unit> {
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.eliminarRaza(id)
            cargarRazas()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al eliminar raza"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun cambiarEstadoRaza(id: Int): Result<Unit> {
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.cambiarEstadoRaza(id)
            cargarRazas()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cambiar estado de la raza"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}

