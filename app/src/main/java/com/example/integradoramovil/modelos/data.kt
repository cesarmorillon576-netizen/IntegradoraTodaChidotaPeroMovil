package com.example.integradoramovil.modelos

// hay que manejar la respuesta o me mato
data class ApiResponse<T>(
    val data: T?,
    val message: String,
    val status: Int,
    val error: String?
)

data class DataPaginada<T>(
    val current_page: Int,
    val data: List<T>,
    val last_page: Int,
    val per_page: Int,
    val total: Int
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

data class RazaDetalle(
    val id: Int,
    val nombre: String,
    val animal: Animal? = null
)

data class Raza(
    val id: Int,
    val nombre: String,
    val visibilidad: String,
    val animal_id: Int?,
    val animal: String? = null
)

data class Animal(
    val id: Int,
    val nombre: String,
    val visibilidad: String? = null
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
// ============================== MMASCOTA =======================
data class Mascota(
    val id: Int,
    val nombre: String,
    val sexo: String? = null,
    val peso: Double? = null,
    val descripcion: String? = null,
    val visibilidad: String? = null,
    val raza: RazaDetalle? = null,
    val cliente: Cliente? = null,
    val deleted_at: String? = null,
    val fecha_nacimiento: String
)


// ===============================================================
data class Cliente(
    val id: Int,
    val municipio: String? = null,
    val colonia: String? = null,
    val codigo_postal: String? = null,
    val calle: String? = null,
    val numero_exterior: String? = null,
    val user: User? = null
)

data class Consulta(
    val id: Int,
    val pre_diagnostico: String? = "",
    val indicaciones: String? = ""
)
// para peticiones
data class AnimalRequest(
    val nombre: String,
    val visibilidad: String? = "visible"
)

data class RazaRequest(
    val nombre: String,
    val animal_id: Int?,
    val visibilidad: String? = "visible"
)

data class MascotaRequest(
    val nombre: String,
    val sexo: String,
    val peso: Double?,
    val fecha_nacimiento: String,
    val descripcion: String,
    val raza_id: Int,
    val cliente_id: Int? = null,
    val visibilidad: String = "visible"
)

 // esto es del diavlo
/* data class CitaRequest(
    val estado: String,
    val fecha: String,
    val tipo: String?,
    val mascota_id: Int,
    val horario_trabajador_id: Int
)*/