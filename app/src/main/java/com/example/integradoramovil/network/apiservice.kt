package com.example.integradoramovil.network

import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.LoginResponse
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.RazaRequest
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET

interface apiservice {

    // rutas de login
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("correo") correo:String,
        @Field("password")password:String
    ): Response<LoginResponse>


    // rutas para razas
    @GET("raza")
    suspend fun obtenerRazas(): List<Raza>

    @POST("raza")
    suspend fun crearRaza(
        @Body raza: RazaRequest
    )



    @FormUrlEncoded
    @PUT("raza")
    suspend fun actualizarRaza(
        @Field("nombre") nombre: String,
        @Field("id_animal") id_animal: Int
    )

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
}