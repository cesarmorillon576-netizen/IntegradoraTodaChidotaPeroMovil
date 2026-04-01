package com.example.integradoramovil.network

import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.ApiResponse
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import com.example.integradoramovil.modelos.Cita
import com.example.integradoramovil.modelos.CitaRequest
import com.example.integradoramovil.modelos.LoginResponse
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.modelos.MascotaRequest
import com.example.integradoramovil.modelos.PaginacionCitas
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET

interface apiservice {

    // rutas de login
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") correo: String,
        @Field("password") password: String
    ): Response<ApiResponse<LoginResponse>>


    // rutas para razas
    @GET("raza/full")
    suspend fun obtenerRazas(): Response<ApiResponse<List<Raza>>>

    @POST("raza/raza")
    suspend fun crearRaza(
        @Body raza: RazaRequest
    ): Response<ApiResponse<Unit>>

    @FormUrlEncoded
    @PUT("raza/raza/{id}")
    suspend fun actualizarRaza(
        @Path("id") id: Int,
        @Field("nombre") nombre: String,
        @Field("animal_id") animal_id: Int?
    ): Response<ApiResponse<Unit>>


    @DELETE("raza/{id}")
    suspend fun eliminarRaza(
        @Path("id") id_raza: Int
    ): Response<ApiResponse<Unit>>


    @PUT("raza/cambiar-estado/{id}")
    suspend fun cambiarEstadoRaza(
        @Path("id") id: Int
    ): Response<ApiResponse<Unit>>

    // rutas para animales
    @GET("animal/full")
    suspend fun obtenerAnimales(): Response<ApiResponse<List<Animal>>>

    @FormUrlEncoded
    @POST("animal/store")
    suspend fun crearAnimal(
        @Field("nombre") nombre: String
    ): Response<ApiResponse<Unit>>

    @FormUrlEncoded
    @PUT("animal/update/{id}")
    suspend fun actualizarAnimal(
        @Path("id") id: Int,
        @Field("nombre") nombre: String
    ): Response<ApiResponse<Unit>>

    @DELETE("animal/delete/{id}")
    suspend fun borrarAnimal(
        @Path("id") id: Int
    ): Response<ApiResponse<Unit>>


    @PUT("animal/cambiar-estado/{id}")
    suspend fun cambiarEstadoAnimal(
        @Path("id") id: Int
    ): Response<ApiResponse<Unit>>

    // rutas para citas

    @POST("cita/search/{history}")
    suspend fun buscarCitas(
        @Path("history") history: Boolean = false,
        @Query("page") pagina: Int,
        @Body filtros: Map<String, String> = emptyMap()
    ): Response<ApiResponse<PaginacionCitas>>

    @PUT("cita/cambiar-estado/{id}")
    suspend fun cambiarEstadoCita(
        @Path("id") id: Int,
        @Field("estado") estado: String
    )

    // rutas de mascotas
    @GET("mascota/index")
    suspend fun obtenerMascotas(
        @Query("page") pagina: Int = 1
    ): Response<ApiResponse<List<Mascota>>>

    @POST("mascota/store")
    suspend fun crearMascota(
        @Body mascota: MascotaRequest
    ): Response<ApiResponse<Mascota>>

    @PUT("mascota/update/{id}")
    suspend fun actualizarMascota(
        @Path("id") id: Int,
        @Body mascota: MascotaRequest
    ): Response<ApiResponse<Mascota>>
}