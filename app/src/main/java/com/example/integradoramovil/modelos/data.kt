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

// hay que manejar las citas
data class PaginacionCitas(
    val current_page: Int,
    val last_page: Int,
    val data: List<Cita>? = emptyList(),
    val total: Int? = 0,
    val per_page: Int? = null
)
data class Cita(
    val id: Int,
    val estado: String = "",
    val fecha: String? = "",
    val tipo: String? = "",
    val descripcion: String? = "",
    val mascota: Mascota? = null,
    val consulta: Consulta? = null,
    val mascota_id: Int? = null,
    val horario_trabajador_id: Int? = null
)

data class Mascota(
    val id: Int,
    val nombre: String,
    val cliente: Cliente? = null,
    val deleted_at: String? = null
)

data class Cliente(
    val id: Int? = null,
    val user: User? = null
)

data class Consulta(
    val id: Int,
    val pre_diagnostico: String? = "",
    val indicaciones: String? = ""
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