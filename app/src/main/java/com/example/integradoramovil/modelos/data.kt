package com.example.integradoramovil.modelos

data class LoginResponse(
    val user: User
)

data class User(
    val id: Int,
    val nombre: String,
    val correo: String,
    val telefono: String,
    //  val estado: String,
    val id_rol: Int
)

data class Raza(
    val id_raza: Int,
    val nombre: String,
    val visibilidad: String,
    val id_animal: Int?,
    val animal: String
)

data class Animal(
    val id_animal: Int,
    val nombre: String,
    val visibilidad: String
)

data class RazaRequest(
    val nombre: String,
    val id_animal: Int?
)
