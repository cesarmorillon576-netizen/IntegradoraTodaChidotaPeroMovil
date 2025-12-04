package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.*
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import com.google.android.material.behavior.SwipeDismissBehavior

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modaleditar(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Int?) -> Unit,
    raza: Raza? = null,
    animal: Animal? = null,
    viewModel: AnimalRazaUserViewModel
){
    var nombre by remember { mutableStateOf(animal?.nombre ?: raza?.nombre ?: "") }

    var expanded by remember {mutableStateOf(false)}
    var animalSeleccionado by remember {mutableStateOf(animal?.nombre ?: "Selecciona un animal")}
    var idAnimalSeleccionado by remember {mutableStateOf<Int?>(raza?.id_animal ?: null)}

    AlertDialog(
        onDismissRequest = onDismissRequest,
        icon = {
            Icon(
                painter = painterResource(R.drawable.agregar),
                contentDescription = if (raza != null) "Editar raza" else "Editar animal"
            )
        },
        title = { Text(if (raza != null) "Editar raza" else "Editar animal") },
        text = {
            Column {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text(if (raza == null) "Nombre del animal" else "Nombre de la raza") }
                )

                if (raza != null) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Animal asociado:")

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = animalSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            viewModel.animales.value.forEach { a ->
                                DropdownMenuItem(
                                    text = { Text(a.nombre) },
                                    onClick = {
                                        animalSeleccionado = a.nombre
                                        idAnimalSeleccionado = a.id_animal
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (raza != null && idAnimalSeleccionado == null) return@TextButton
                    onConfirmation(nombre, idAnimalSeleccionado)
                }
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancelar") }
        }
    )
}

