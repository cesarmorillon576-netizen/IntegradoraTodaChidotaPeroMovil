package com.example.integradoramovil.modelos

data class Usuario(
    val nombre: String,
    val correo: String
)

data class raza(
    val id_raza: Int,
    val nombre: String,
    val visibilidad: String,
    val id_animal: Int,
    val animal: animal
)

data class animal(
    val id_animal: Int,
    val nombre: String,
    val visibilidad: String
)
