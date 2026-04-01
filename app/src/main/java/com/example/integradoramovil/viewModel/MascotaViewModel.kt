package com.example.integradoramovil.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.repositorios.MascotaRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MascotaViewModel(
    private val repository: MascotaRepository
): ViewModel() {
    // las cosas del flujo
    val mascotas: StateFlow<List<Mascota>> = repository.mascotas
    val isLoading: StateFlow<Boolean> = repository.isLoading
    val error: StateFlow<String?> = repository.error

    // LOS CATALOGOS
    val animales: StateFlow<List<Animal>> = repository.animales
    private val fullRazas: StateFlow<List<Raza>> = repository.razas

    var animalSeleccionadoId by mutableStateOf<Int?>(null)
    var razaSeleccionadaId by mutableStateOf<Int?>(null)

    var razasFiltradas by mutableStateOf<List<Raza>>(emptyList())

    init{
        fullLoad()
    }

    fun fullLoad(){
        viewModelScope.launch{
            repository.cargarMascotas()
            repository.cargarAnimales()
            repository.cargarRazas()
        }
    }

    fun seleccionarAnimal(id: Int){
        this.animalSeleccionadoId = id
        razaSeleccionadaId = null

        razasFiltradas = fullRazas.value.filter{ animal ->
            animal.animal_id == id
        }
    }

    fun seleccionarRaza(id: Int){

    }

}