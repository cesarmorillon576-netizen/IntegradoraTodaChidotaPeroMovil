package com.example.integradoramovil.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.AuthManager
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.modelos.Mascota
import com.example.integradoramovil.modelos.MascotaRequest
import com.example.integradoramovil.ui.theme.*
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import com.example.integradoramovil.viewModel.MascotaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modal(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Int?, String) -> Unit,
    raza: Raza?,
    viewModel: AnimalRazaUserViewModel
) {
    var nombre by rememberSaveable { mutableStateOf(raza?.nombre ?: "") }
    var visibilidadSeleccionada by rememberSaveable {
        mutableStateOf(raza?.visibilidad ?: "visible")
    }

    // estados para los dropdowns
    var expandedAnimal by remember { mutableStateOf(false) }
    var expandedVisibilidad by remember { mutableStateOf(false) }

    var animalSeleccionadoNombre by remember {
        mutableStateOf(raza?.animal ?: "Selecciona un animal")
    }
    var idAnimalSeleccionado by remember { mutableStateOf<Int?>(raza?.animal_id) }

    val listaAnimales by viewModel.animales.collectAsState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = BackgroundCard,
        title = {
            Text(
                text = if (raza != null) "Configurar Raza" else "Configurar Animal",
                fontFamily = baloo,
                fontWeight = FontWeight.ExtraBold,
                color = TextMain
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it.filter { c -> c.isLetter() || c.isWhitespace() } },
                    label = { Text(if (raza == null) "Nombre del animal" else "Nombre de la raza") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange)
                )

                if (raza != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Pertenece al animal:", fontSize = 14.sp, color = TextSub, fontFamily = baloo)
                    ExposedDropdownMenuBox(
                        expanded = expandedAnimal,
                        onExpandedChange = { expandedAnimal = !expandedAnimal }
                    ) {
                        OutlinedTextField(
                            value = animalSeleccionadoNombre,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedAnimal) },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedAnimal,
                            onDismissRequest = { expandedAnimal = false }
                        ) {
                            listaAnimales.forEach { animal ->
                                DropdownMenuItem(
                                    text = { Text(animal.nombre) },
                                    onClick = {
                                        animalSeleccionadoNombre = animal.nombre
                                        idAnimalSeleccionado = animal.id
                                        expandedAnimal = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Visibilidad:", fontSize = 14.sp, color = TextSub, fontFamily = baloo)
                ExposedDropdownMenuBox(
                    expanded = expandedVisibilidad,
                    onExpandedChange = { expandedVisibilidad = !expandedVisibilidad }
                ) {
                    OutlinedTextField(
                        value = visibilidadSeleccionada.replaceFirstChar { it.uppercase() },
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedVisibilidad) },
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedVisibilidad,
                        onDismissRequest = { expandedVisibilidad = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Visible", color = TextMain) },
                            onClick = {
                                visibilidadSeleccionada = "visible"
                                expandedVisibilidad = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Invisible", color = TextMain) },
                            onClick = {
                                visibilidadSeleccionada = "invisible"
                                expandedVisibilidad = false
                            }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombre.isNotBlank() && (raza == null || idAnimalSeleccionado != null)) {
                        onConfirmation(nombre, idAnimalSeleccionado, visibilidadSeleccionada)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("Guardar", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancelar", color = TextSub) }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modal(
    onDismissRequest: () -> Unit,
    onConfirmation: (MascotaRequest) -> Unit,
    mascota: Mascota? = null,
    viewModel: MascotaViewModel
){
    var nombre by remember {mutableStateOf(mascota?.nombre ?: "")}
    var sexo by remember {mutableStateOf(mascota?.sexo ?: "Macho")}
    var peso by remember {mutableStateOf(mascota?.peso?.toString() ?: "")}
    var descripcion by remember {mutableStateOf(mascota?.descripcion ?: "")}
    var fechaNacimiento by remember { mutableStateOf(mascota?.fecha_nacimiento ?: "Seleccionar fecha") }

    val datePickerState = rememberDatePickerState()
    var mostrarDatePicker by remember { mutableStateOf(false) }

    val listaAnimales by viewModel.animales.collectAsState()
    val razasFiltradas = viewModel.razasFiltradas

    var animalExpandido by remember {mutableStateOf(false)}
    var razaExpandido by remember {mutableStateOf(false)}
    var sexoExpandido by remember { mutableStateOf(false) }

    var animalSeleccionadoNombre by remember { mutableStateOf(mascota?.raza?.animal?.nombre ?: "Selecciona Animal") }

    var razaSeleccionada by remember { mutableStateOf<Any?>(mascota?.raza) }

    // clientes
    val listaClientes by viewModel.clientes.collectAsState()
    var clienteExpandido by remember {mutableStateOf(false)}
    var clienteSeleccionado by remember {
        mutableStateOf(mascota?.cliente?.user?.nombre ?: "Seleccionar dueño")
    }

    var idClienteSelecionado: Int? by remember {mutableStateOf<Int?>(mascota?.cliente?.id ?: 0)}

    LaunchedEffect(mascota) {
        mascota?.raza?.animal?.id?.let { id -> viewModel.seleccionarAnimal(id) }
    }

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        fechaNacimiento = sdf.format(Date(millis))
                    }
                    mostrarDatePicker = false
                }) { Text("OK", color = PrimaryOrange) }
            },
            dismissButton = { TextButton(onClick = { mostrarDatePicker = false }) { Text("Cancelar") } }
        ) { DatePicker(state = datePickerState) }
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = BackgroundCard,
        title = { Text(text = if(mascota != null) "Editar mascota" else "Nueva mascota", fontFamily = baloo, fontWeight = FontWeight.ExtraBold, color = TextMain) },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())){
                OutlinedTextField(value = nombre, onValueChange = {nombre = it}, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange))

                Spacer(modifier = Modifier.height(12.dp))

                // selector de dueño
                if (AuthManager.isAdmin() || AuthManager.isTrabajador()) {
                    Text(text = "Dueño de la mascota:", fontSize = 14.sp, color = TextSub, fontFamily = baloo)
                    ExposedDropdownMenuBox(
                        expanded = clienteExpandido,
                        onExpandedChange = { clienteExpandido = !clienteExpandido }
                    ) {
                        OutlinedTextField(
                            value = clienteSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Cliente") },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = clienteExpandido) },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange)
                        )
                        ExposedDropdownMenu(
                            expanded = clienteExpandido,
                            onDismissRequest = { clienteExpandido = false },
                            modifier = Modifier.background(BackgroundCard)
                        ) {
                            listaClientes.forEach { cliente ->
                                DropdownMenuItem(
                                    text = { Text(cliente.user?.nombre ?: "Sin nombre", color = TextMain) },
                                    onClick = {
                                        clienteSeleccionado = cliente.user?.nombre ?: ""
                                        idClienteSelecionado = cliente?.id
                                        clienteExpandido = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Selector de Animal
                ExposedDropdownMenuBox(expanded = animalExpandido, onExpandedChange = { animalExpandido = !animalExpandido }) {
                    OutlinedTextField(value = animalSeleccionadoNombre, onValueChange = {}, readOnly = true, label = { Text("Tipo de Animal") }, modifier = Modifier.menuAnchor().fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = animalExpandido) }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange))
                    ExposedDropdownMenu(expanded = animalExpandido, onDismissRequest = { animalExpandido = false }, modifier = Modifier.background(BackgroundCard)) {
                        listaAnimales.forEach { animal ->
                            DropdownMenuItem(text = { Text(animal.nombre, color = TextMain) }, onClick = {
                                animalSeleccionadoNombre = animal.nombre
                                viewModel.seleccionarAnimal(animal.id)
                                razaSeleccionada = null
                                animalExpandido = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Selector de Raza
                ExposedDropdownMenuBox(expanded = razaExpandido, onExpandedChange = { if(viewModel.animalSeleccionadoId != null) razaExpandido = !razaExpandido }) {
                    val nombreRaza = when(val r = razaSeleccionada) {
                        is Raza -> r.nombre
                        else -> mascota?.raza?.nombre ?: "Selecciona Raza"
                    }
                    OutlinedTextField(value = nombreRaza, onValueChange = {}, readOnly = true, label = { Text("Raza") }, enabled = viewModel.animalSeleccionadoId != null, modifier = Modifier.menuAnchor().fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = razaExpandido) }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange))
                    ExposedDropdownMenu(expanded = razaExpandido, onDismissRequest = { razaExpandido = false }, modifier = Modifier.background(BackgroundCard)) {
                        razasFiltradas.forEach { raza ->
                            DropdownMenuItem(text = { Text(raza.nombre, color = TextMain) }, onClick = {
                                razaSeleccionada = raza
                                razaExpandido = false
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = fechaNacimiento, onValueChange = {}, readOnly = true, label = { Text("Fecha de nacimiento") }, modifier = Modifier.fillMaxWidth(),
                    trailingIcon = { IconButton(onClick = { mostrarDatePicker = true }) { Icon(painter = painterResource(id = R.drawable.agregar), contentDescription = null, modifier = Modifier.size(20.dp)) } },
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(value = peso, onValueChange = {peso = it}, label = { Text("Peso (kg)")}, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange))
                    Spacer(modifier = Modifier.width(12.dp))
                    ExposedDropdownMenuBox(expanded = sexoExpandido, onExpandedChange = { sexoExpandido = !sexoExpandido }, modifier = Modifier.weight(1f)) {
                        OutlinedTextField(value = sexo, onValueChange = {}, readOnly = true, label = { Text("Sexo") }, modifier = Modifier.menuAnchor().fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sexoExpandido) }, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange))
                        ExposedDropdownMenu(expanded = sexoExpandido, onDismissRequest = { sexoExpandido = false }, modifier = Modifier.background(BackgroundCard)) {
                            listOf("Macho", "Hembra").forEach { opcion -> DropdownMenuItem(text = { Text(opcion, color = TextMain) }, onClick = { sexo = opcion; sexoExpandido = false }) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), minLines = 3, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = PrimaryOrange))
            }
        },
        confirmButton = {
            Button(onClick = {
                val idFinal = when(val r = razaSeleccionada) {
                    is Raza -> r.id
                    else -> mascota?.raza?.id
                }
                if(nombre.isNotBlank() && idFinal != null){
                    onConfirmation(MascotaRequest(nombre, sexo.toLowerCase(), peso.toDoubleOrNull(), if(fechaNacimiento == "Seleccionar fecha") "2024-01-01" else fechaNacimiento, descripcion, idFinal, cliente_id = if(!AuthManager.isAdmin()) null else 1))
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)) { Text("Guardar", color = Color.White) }
        },
        dismissButton = { TextButton(onClick = onDismissRequest) { Text("Cancelar", color = TextSub) } }
    )
}