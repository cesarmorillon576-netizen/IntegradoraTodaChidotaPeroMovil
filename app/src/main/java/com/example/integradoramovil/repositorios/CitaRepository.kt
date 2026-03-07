package com.example.integradoramovil.repositorios

import com.example.integradoramovil.network.RetroFitClient
import com.example.integradoramovil.modelos.CitaRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList
import com.example.integradoramovil.modelos.Cita
class CitaRepository {
    private val estados: List<String> = listOf("agendada", "realizada","en_proceso", "cancelada")
    
    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun cargarCitas(){
        _isLoading.value = true
        _error.value = null
        try{
            val res = RetroFitClient.api.obtenerCitas()
            _citas.value = res
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cargar citas"

        }finally{
            _isLoading.value = false
        }
    }

    suspend fun modificarCita(/*cita: CitaRequest*/){
        // aqui va algo
    }

    suspend fun cambiarEstadoCita(id: Int, estado: String): Result<Unit> {
        if (estado !in estados) {
            _error.value = "Estado no valido"
            return Result.failure(Exception("Estado no valido"))
        }
        _isLoading.value = true
        _error.value = null
        return try {
            RetroFitClient.api.cambiarEstadoCita(id, estado)
            cargarCitas()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cambiar estado"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }


}