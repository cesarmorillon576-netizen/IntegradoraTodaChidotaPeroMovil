package com.example.integradoramovil.network

import com.example.integradoramovil.modelos.animal
import com.example.integradoramovil.modelos.raza
import retrofit2.http.*
import retrofit2.http.GET

interface apiservice {

    // rutas para razas
    @GET("raza")
    suspend fun obtenerRazas(): List<raza>

    @FormUrlEncoded
    @POST("raza")
    suspend fun crearRaza(
        @Field("nombre") nombre: String,
        @Field("id_animal") id_animal: Int
    ): raza

    @FormUrlEncoded
    @PUT("raza")
    suspend fun actualizarRaza(
        @Field("nombre") nombre: String,
        @Field("id_animal") id_animal: Int
    ): raza

    @FormUrlEncoded
    @DELETE("raza")
    suspend fun eliminarRaza(
        @Field("id_raza") id_raza: Int
    )

    @FormUrlEncoded
    @PUT("cambiar-estado/{id_raza}")
    suspend fun cambiarEstadoRaza(
        @Field("id_raza") id_raza: Int
    )

    // rutas para animales
    @GET("animal")
    suspend fun obtenerAnimales(): List<animal>

    @FormUrlEncoded
    @POST("animal")
    suspend fun crearAnimal(
        @Field("nombre") nombre: String
    )

    @FormUrlEncoded
    @PUT("animal")
    suspend fun actualizarAnimal(
        @Field("nombre") nombre: String
    )

    @FormUrlEncoded
    @DELETE("animal")
    suspend fun borrarAnimal(
        @Field("id") id: Int
    )
}