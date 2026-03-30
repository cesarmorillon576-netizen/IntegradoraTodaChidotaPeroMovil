
package com.example.integradoramovil.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.modelos.Cita
import com.example.integradoramovil.network.RetroFitClient
import com.example.integradoramovil.repositorios.CitaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CitaViewModel(
    private val repository: CitaRepository
): ViewModel(){
    val citas = repository.citas
    val isLoading: StateFlow<Boolean> = repository.isLoading
    val error: StateFlow<String?> = repository.error

    val paginaActual: StateFlow<Int> = repository.paginaActual
    val ultimaPagina = repository.ultimaPagina

    init{
        cargarCitas(1)
    }

    fun cargarCitas(pagina: Int = 1){
        viewModelScope.launch{
            repository.cargarCitas(pagina)
        }
    }

    fun cambiarPagina(nuevaPagina: Int){
        cargarCitas(nuevaPagina)
    }

    fun cambiarEstado(id: Int, estado: String){
        viewModelScope.launch{
            repository.cambiarEstadoCita(id, estado)
        }
    }

    fun modificarCita(/* aqui van a ir parametros que no se cuales son */){
        viewModelScope.launch{
            repository.modificarCita()
        }
    }

    companion object Factory {
        fun create(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val api = com.example.integradoramovil.network.RetroFitClient.getApi(context)
                    val repository = com.example.integradoramovil.repositorios.CitaRepository(api)
                    return CitaViewModel(repository) as T
                }
            }
        }
    }
}
