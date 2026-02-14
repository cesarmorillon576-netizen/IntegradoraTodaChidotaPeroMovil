package com.example.integradoramovil.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.*
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.BackgroundCard2
import com.example.integradoramovil.ui.theme.dropDownBackground
import com.example.integradoramovil.ui.theme.redText
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
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
    val animales by viewModel.animales.collectAsState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = Background,
        icon = {
            Icon(
                painter = painterResource(R.drawable.agregar),
                tint = redText,
                contentDescription = if (raza != null) "Editar raza" else "Editar animal"
            )
        },
        title = {
            Text(if (raza != null) {
                "Editar raza"
            }else {
                "Editar animal"
            },
                color = redText
            ) },
        text = {
            Column {
                TextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it.filter {
                            it.isLetter() || it.isWhitespace()
                        }
                                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = BackgroundCard2,
                        focusedContainerColor = BackgroundCard2,
                        cursorColor = redText,
                        focusedIndicatorColor = redText
                    ),
                    textStyle = TextStyle(
                        color = textColorsubMain
                    ),
                    label = { Text( color = redText, text = if (raza == null) "Nombre del animal"
                    else "Nombre de la raza") },
                )

                if (raza != null) {
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Animal asociado:",
                        color = redText)

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = dropDownBackground,
                                focusedContainerColor = dropDownBackground,
                                cursorColor = textColor,
                                focusedIndicatorColor = textColor
                            ),
                            textStyle = TextStyle(
                                color = textColor
                            ),
                            value = animalSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            animales.forEach { a ->
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
            ) { Text("Guardar",
                color = redText) }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) { Text("Cancelar",
                color = redText) }
        }
    )
}

