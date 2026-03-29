package com.example.integradoramovil.modelos

// hay que manejar la respuesta o me mato
data class ApiResponse<T>(
    val data: T?,
    val message: String,
    val status: Int,
    val error: String?
)


// login
data class LoginResponse(
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String,
    //  val estado: String,
    val rol_id: Int,
    val rol: Rol
)

// catalogos
data class Rol(
    val id: Int,
    val nombre: String
)

data class Raza(
    val id: Int,
    val nombre: String,
    val visibilidad: String,
    val animal_id: Int?,
    val animal: String
)

data class Animal(
    val id: Int,
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

// para peticiones
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