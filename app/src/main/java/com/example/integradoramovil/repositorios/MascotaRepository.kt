package com.example.integradoramovil.repositorios

import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Cliente
import com.example.integradoramovil.modelos.DataPaginada
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.modelos.MascotaRequest
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.network.apiservice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MascotaRepository(private val api: apiservice) {

    // hay que dejarse llevar por el flow
    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas = _mascotas.asStateFlow()

    private val _paginacion = MutableStateFlow<DataPaginada<Mascota>?>(null)
    val paginacion = _paginacion.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // catalogos
    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes = _clientes.asStateFlow() // se me olvidaronnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
    private val _animales = MutableStateFlow<List<Animal>>(emptyList())
    val animales = _animales.asStateFlow()

    private val _razas = MutableStateFlow<List<Raza>>(emptyList())
    val razas = _razas.asStateFlow()

    // funciones de catalogos/carga

    suspend fun cargarClientes(){
        ejecutarConEstado {
            val res = api.obtenerClientes()
            if(res.isSuccessful){
                res.body()?.data?.let{ clientes ->
                    _clientes.value = clientes
                }
            }else{
                manejarErrorApi(res.errorBody()?.string(), "Error al cargar clientes")
            }
        }
    }

    suspend fun cargarMascotas(pagina: Int = 1) {
        ejecutarConEstado {
            val res = api.obtenerMascotas(pagina)
            if (res.isSuccessful) {
                res.body()?.data?.let { info ->
                    _mascotas.value = info.data
                    _paginacion.value = info
                }
            } else {
                manejarErrorApi(res.errorBody()?.string(), "Error al cargar mascotas")
            }
        }
    }

    suspend fun cargarRazas() {
        try {
            val res = api.obtenerRazas()
            if (res.isSuccessful) {
                _razas.value = res.body()?.data ?: emptyList()
            } /*else {
                manejarErrorApi(res.errorBody()?.string(), "Error al cargar razas")
            }*/
        } catch (e: Exception) {
            println("Error... ${e.message}")
        }
    }

    suspend fun cargarAnimales() {
        try {
            val res = api.obtenerAnimales()
            if (res.isSuccessful) {
                _animales.value = res.body()?.data ?: emptyList()
            } else {
                manejarErrorApi(res.errorBody()?.string(), "Error al cargar animales")
            }
        } catch (e: Exception) {
            _error.value = e.message ?: "Error de conexión en animales"
        }
    }

    // todas las funciones del crud

    suspend fun guardarMascota(mascota: MascotaRequest) {
        ejecutarConEstado {
            val res = api.crearMascota(mascota)
            if (res.isSuccessful) {
                cargarMascotas()
            } else {
                manejarErrorApi(res.errorBody()?.string(), "Error al guardar mascota")
            }
        }
    }

    suspend fun editarMascota(mascota: MascotaRequest, id: Int) {
        ejecutarConEstado {
            val res = api.actualizarMascota(id, mascota)
            if (res.isSuccessful) {
                cargarMascotas()
            } else {
                manejarErrorApi(res.errorBody()?.string(), "Error al editar mascota")
            }
        }
    }

    suspend fun eliminarMascota(id: Int) {
        ejecutarConEstado {
            val res = api.eliminarMascota(id)
            if (res.isSuccessful) {
                _mascotas.value = _mascotas.value.filter { it.id != id }
                cargarMascotas(pagina = _paginacion.value?.current_page ?: 1)
            } else {
                manejarErrorApi(res.errorBody()?.string(), "Error al eliminar mascota")
            }
        }
    }

    // otros metodos
    suspend fun ejecutarConEstado(block: suspend () -> Unit) {
        try {
            _isLoading.value = true
            _error.value = null
            block()
        } catch (e: Exception) {
            _error.value = e.message ?: "Error inesperado"
            println("DEBUG_REPO_EXCEPTION: ${e.message}")
        } finally {
            _isLoading.value = false
        }
    }

    fun manejarErrorApi(errorBody: String?, mensajeDefault: String) {
        val errorMsg = errorBody ?: mensajeDefault
        _error.value = errorMsg
        println("DEBUG_API_ERROR: $errorMsg")
    }
}