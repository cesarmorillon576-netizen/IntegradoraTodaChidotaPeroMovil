package com.example.integradoramovil.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.*
import com.example.integradoramovil.ui.theme.*
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modaleditar(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Int?) -> Unit,
    raza: Raza? = null,
    animal: Animal? = null,
    viewModel: AnimalRazaUserViewModel
) {

    var nombre by remember { mutableStateOf(raza?.nombre ?: animal?.nombre ?: "") }
    var expanded by remember { mutableStateOf(false) }

    val animales by viewModel.animales.collectAsState()

    var idAnimalSeleccionado by remember { mutableStateOf<Int?>(raza?.animal_id) }
    var animalSeleccionado by remember {
        mutableStateOf(animales.find { it.id == raza?.animal_id }?.nombre ?: "Selecciona un animal")
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = BackgroundCard,
        icon = {
            Icon(
                painter = painterResource(R.drawable.agregar),
                tint = PrimaryOrange,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        },
        title = {
            Text(
                text = if (raza != null) "Editar Raza" else "Editar Animal",
                color = TextMain,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = baloo
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it.filter { c -> c.isLetter() || c.isWhitespace() }
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(color = TextMain),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        unfocusedBorderColor = TextSub.copy(alpha = 0.3f),
                        focusedLabelColor = PrimaryOrange,
                        cursorColor = PrimaryOrange,
                        focusedTextColor = TextMain,
                        unfocusedTextColor = TextMain
                    )
                )

                if (raza != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Animal asociado",
                        color = TextSub,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = animalSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = TextSub.copy(alpha = 0.3f),
                                focusedTextColor = TextMain,
                                unfocusedTextColor = TextMain
                            ),
                            textStyle = TextStyle(color = TextMain)
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(BackgroundCard)
                        ) {
                            animales.forEach { item ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = item.nombre,
                                            color = TextMain,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    },
                                    onClick = {
                                        animalSeleccionado = item.nombre
                                        idAnimalSeleccionado = item.id
                                        expanded = false
                                    },
                                    modifier = Modifier.background(BackgroundCard)
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nombre.isNotBlank() && (raza == null || idAnimalSeleccionado != null)) {
                        onConfirmation(nombre, idAnimalSeleccionado)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
            ) {
                Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar", color = TextSub)
            }
        }
    )
}