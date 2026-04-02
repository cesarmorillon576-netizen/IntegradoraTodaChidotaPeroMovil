package com.example.integradoramovil.viewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Cliente
import com.example.integradoramovil.modelos.MascotaRequest
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
    val paginacion = repository.paginacion

    // LOS CATALOGOS
    val clientes: StateFlow<List<Cliente>> = repository.clientes
    val animales: StateFlow<List<Animal>> = repository.animales
    private val fullRazas: StateFlow<List<Raza>> = repository.razas

    // para el modal
    var animalSeleccionadoId by mutableStateOf<Int?>(null)
    var razaSeleccionadaId by mutableStateOf<Int?>(null)

    var razasFiltradas by mutableStateOf<List<Raza>>(emptyList())

    init{
        fullLoad()
    }

    fun fullLoad(){
        viewModelScope.launch {
            launch { repository.cargarMascotas(1) }
            launch { repository.cargarAnimales() }
            launch { repository.cargarRazas() }
            launch {
                try {
                    repository.cargarClientes()
                } catch(e: Exception) {
                    Log.e("API_ERROR", "Clientes falló pero razas debería seguir: ${e.message}")
                }
            }
        }
    }

    fun cambiarPagina(pagina: Int){
        viewModelScope.launch {
            repository.cargarMascotas(pagina)
        }
    }

    fun eliminarMasota(id: Int){
        viewModelScope.launch{
            repository.eliminarMascota(id)
        }
    }

    fun seleccionarAnimal(id: Int){
        this.animalSeleccionadoId = id
        razaSeleccionadaId = null

        razasFiltradas = fullRazas.value.filter{ animal ->
            animal.animal_id == id
        }
    }

    fun GuardarMascota(request: MascotaRequest){
        viewModelScope.launch {
            repository.guardarMascota(request)
        }
    }

    fun editarMascota(request: MascotaRequest, id: Int){
        viewModelScope.launch {
            repository.editarMascota(request, id)
        }
    }

    companion object {
        fun create(context: Context): ViewModelProvider.Factory{
            return object: ViewModelProvider.Factory{
                @Suppress("UNCHECKED_CAST")
                override fun <T: ViewModel> create(modelClass: Class<T>): T {
                    val api = com.example.integradoramovil.network.RetroFitClient.getApi(context)
                    val repository = MascotaRepository(api)
                    return MascotaViewModel(repository) as T
                }

            }
        }
    }

}