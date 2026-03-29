package com.example.integradoramovil.repositorios

import android.content.Context
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import com.example.integradoramovil.network.RetroFitClient
import com.example.integradoramovil.network.apiservice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList

class AnimalRazaRepository(private val api: apiservice) {

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
        try{
            val res = api.obtenerAnimales()
            if(res.isSuccessful){
                _animales.value = res.body()?.data ?: emptyList()
            }else{
                _error.value = "Error: ${res.code()}"
            }
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
            val response = api.crearAnimal(nombre)
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
            api.actualizarAnimal(animal.id, animal.nombre)
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
            api.borrarAnimal(id)
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
            api.cambiarEstadoAnimal(id)
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
            val res = api.obtenerRazas()
            if(res.isSuccessful){
                _razas.value = res.body()?.data ?: emptyList()
            }else{
                _error.value = "Error: ${res.code()}"
            }
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
            val razaRequest = RazaRequest(raza.nombre, raza.animal_id)
            api.crearRaza(razaRequest)
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
            api.actualizarRaza(raza.id, raza.nombre, raza.animal_id)
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
            api.eliminarRaza(id)
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
            api.cambiarEstadoRaza(id)
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

