package com.example.integradoramovil.modelos

// hay que manejar la respuesta o me mato
data class ApiResponse<T>(
    val data: T?,
    val mensaje: String,
    val status: Int,
    val error: String?
)

data class TokenData(
    val token: String
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

data class Cita(
    val id: Int,
    val estado: String,
    val fecha: String,
    val tipo: String?,
    val mascota_id: Int,
    val horario_trabajador_id: Int
)

data class Mascota(
    val id: Int,
    val nombre: String,
    val peso: Float,
    val descripcion: String,
    val fecha_nacimiento: String,
    val visibilidad: String?,
    val raza: Raza
)

data class RazaRequest(
    val nombre: String,
    val id_animal: Int?
)

data class CitaRequest(
    val estado: String,
    val fecha: String,
    val tipo: String?,
    val mascota_id: Int,
    val horario_trabajador_id: Int
)