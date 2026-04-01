package com.example.integradoramovil.repositorios

import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.network.apiservice
import com.example.integradoramovil.modelos.Cita
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.emptyList
class CitaRepository(private val api: apiservice) {
    private val estados: List<String> = listOf("agendada", "realizada","en_proceso", "cancelada")

    // lista de citas
    private val _citas = MutableStateFlow<List<Cita>>(emptyList())
    val citas: StateFlow<List<Cita>> = _citas.asStateFlow()
    // paginacion
    private val _paginaActual = MutableStateFlow(1)
    val paginaActual = _paginaActual.asStateFlow()

    private val _ultimaPagina = MutableStateFlow(1)
    val ultimaPagina = _ultimaPagina.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun cargarCitas(pagina: Int = 1, historial: Boolean = false){
        _isLoading.value = true
        _error.value = null
        try{
            android.util.Log.d("DEBUG_CITAS", "haciendo la peticion")
            val res = api.buscarCitas(
                history = historial,
                pagina = pagina,
                filtros = emptyMap()
            )
            android.util.Log.d("DEBUG_CITAS", "peticion hecha: ${res.body()}")
            if(res.isSuccessful){
                val resBody = res.body()
                if(resBody != null){
                    val paginacion = resBody.data
                    if(paginacion != null){
                        _citas.value = paginacion.data ?: emptyList()
                        _paginaActual.value = paginacion.current_page
                        _ultimaPagina.value = paginacion.last_page
                        android.util.Log.d("DEBUG_CITAS", "Citas recibidas: ${paginacion.data?.size}")
                    }
                }

            }else{
                _error.value = "Error al cargar citas"
            }

        } catch (e: Exception) {
            android.util.Log.e("DEBUG_CITAS", "ERROR CARGANDO: ${e.message}", e)
            e.printStackTrace()
            _error.value = e.message ?: "Error al cargar citas"

        }finally{
            _isLoading.value = false
        }
    }


    suspend fun cambiarEstadoCita(id: Int, estado: String): Result<Unit> {
        if (estado !in estados) {
            _error.value = "Estado no valido"
            return Result.failure(Exception("Estado no valido"))
        }
        _isLoading.value = true
        _error.value = null
        return try {
            api.cambiarEstadoCita(id, estado)
            cargarCitas()
            Result.success(Unit)
        } catch (e: Exception) {
            _error.value = e.message ?: "Error al cambiar estado"
            Result.failure(e)
        } finally {
            _isLoading.value = false
        }
    }

    fun clearError() {
        _error.value = null
    }
}
