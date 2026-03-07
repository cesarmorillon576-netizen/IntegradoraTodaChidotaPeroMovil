
package com.example.integradoramovil.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.modelos.Cita
import com.example.integradoramovil.repositorios.CitaRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CitaViewModel(
    private val repository: CitaRepository = CitaRepository()
): ViewModel(){
    val citas: StateFlow<List<Cita>> = repository.citas
    val isLoading: StateFlow<Boolean> = repository.isLoading
    val error: StateFlow<String?> = repository.error
    
    init{
        cargarCitas()
    }

    fun cargarCitas(){
        viewModelScope.launch{
            repository.cargarCitas()
        }
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
}
