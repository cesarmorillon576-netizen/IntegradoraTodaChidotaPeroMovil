package com.example.integradoramovil.modelos

data class Usuario(
    val nombre: String,
    val correo: String
)

data class Raza(
    val id_raza: Int,
    val nombre: String,
    val visibilidad: String,
    val id_animal: Int,
    val animal: String
)

data class Animal(
    val id_animal: Int,
    val nombre: String,
    val visibilidad: String
)
