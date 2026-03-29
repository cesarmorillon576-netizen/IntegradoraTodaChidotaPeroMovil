package com.example.integradoramovil.network

import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.ApiResponse
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import com.example.integradoramovil.modelos.Cita
import com.example.integradoramovil.modelos.CitaRequest
import com.example.integradoramovil.modelos.TokenData
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET

interface apiservice {

    // rutas de login
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") correo:String,
        @Field("password")password:String
    ): Response<ApiResponse<TokenData>>


    // rutas para razas
    @GET("raza/raza")
    suspend fun obtenerRazas(): List<Raza>

    @POST("raza/raza")
    suspend fun crearRaza(
        @Body raza: RazaRequest
    )



    @FormUrlEncoded
    @PUT("raza/raza/{id}")
    suspend fun actualizarRaza(
        @Path("id") id_raza: Int,
        @Field("nombre") nombre: String,
        @Field("id_animal") id_animal: Int?
    )


    @DELETE("raza/{id}")
    suspend fun eliminarRaza(
        @Path("id_raza") id_raza: Int
    )


    @PUT("raza/cambiar-estado/{id_raza}")
    suspend fun cambiarEstadoRaza(
        @Path("id_raza") id_raza: Int
    )

    // rutas para animales
    @GET("animal")
    suspend fun obtenerAnimales():  List<Animal>

    @FormUrlEncoded
    @POST("animal")
    suspend fun crearAnimal(
        @Field("nombre") nombre: String
    )

    @FormUrlEncoded
    @PUT("animal/{id}")
    suspend fun actualizarAnimal(
        @Path("id") id: Int,
        @Field("nombre") nombre: String
    )

    @DELETE("animal/{id}")
    suspend fun borrarAnimal(
        @Path("id") id: Int
    )


    @PUT("animal/cambiar-estado/{id}")
    suspend fun cambiarEstadoAnimal(
        @Path("id") id: Int
    )

    // rutas para citas

    @GET("cita")
    suspend fun obtenerCitas(): List<Cita>

    @POST("cita")
    suspend fun crearCita(
        @Body cita: CitaRequest
    )

    @PUT("cita/{id}")
    suspend fun actualizarCita(
        @Path("id") id: Int,
        @Body cita: CitaRequest
    )

    @PUT("cita/cambiar-estado/{id}")
    suspend fun cambiarEstadoCita(
        @Path("id") id: Int,
        @Field("estado") estado: String 
    )
}