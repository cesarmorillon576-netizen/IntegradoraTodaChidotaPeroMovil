package com.example.integradoramovil.repositorios

import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.modelos.MascotaRequest
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.network.apiservice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MascotaRepository(private val api: apiservice){
    // lista de mascotas
    private val _mascotas = MutableStateFlow<List<Mascota>>(emptyList())
    val mascotas = _mascotas.asStateFlow()
    // manejo de estados
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    // catalogos
    private val _animales = MutableStateFlow<List<Animal>>(emptyList())
    val animales = _animales.asStateFlow()
    private val _razas = MutableStateFlow<List<Raza>>(emptyList())
    val razas = _razas.asStateFlow()

    suspend fun cargarMascotas(){
        try{
            _isLoading.value = true
            val res = api.obtenerMascotas()
            if(res.isSuccessful){
                _mascotas.value = res.body()?.data ?: emptyList()
            }
        }catch(e: Exception){
            _error.value = e.message ?: "Error al cargar mascotas"
        }finally {
            _isLoading.value = false
        }
    }

    suspend fun guardarMascota(mascota: MascotaRequest){
        try{
            _isLoading.value = true
            val res = api.crearMascota(mascota)
            if(res.isSuccessful){
                cargarMascotas()
            }
        }catch(e: Exception){
            _error.value = e.message ?: "Error guardando la mascota"
        }finally {
            _isLoading.value = false
        }
    }

    suspend fun editarMascota(mascota: MascotaRequest, id: Int){
        try{
            _isLoading.value = true
            val res = api.actualizarMascota(id, mascota)
            if(res.isSuccessful){
                cargarMascotas()
            }
        }catch(e: Exception){
            _error.value = e.message ?: "Error guardando la mascota"
        }finally {
            _isLoading.value = false
        }
    }

    suspend fun cargarRazas(){
        try{
            val res = api.obtenerRazas()
            if(res.isSuccessful){
                _razas.value = res.body()?.data ?: emptyList()
            }
        }catch(e: Exception){
            _error.value = e.message ?: "Error al cargar razas"
        }
    }

    suspend fun cargarAnimales(){
        try{
            val res = api.obtenerAnimales()
            if(res.isSuccessful){
                _animales.value = res.body()?.data ?: emptyList()
            }
        }catch(e: Exception){
            _error.value = e.message ?: "Error al cargar animales"
        }
    }
}