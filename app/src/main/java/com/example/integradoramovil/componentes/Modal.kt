package com.example.integradoramovil.componentes

import androidx.appcompat.widget.DialogTitle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.BackgroundCard2
import com.example.integradoramovil.ui.theme.dropDownBackground
import com.example.integradoramovil.ui.theme.redText
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textInputOrange
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel

// modal agregar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun modal(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Int?) -> Unit,
    raza: Raza?,
    viewModel: AnimalRazaUserViewModel
){
    var nombre by remember { mutableStateOf("") }

    var expanded by remember {mutableStateOf(false)}
    var animalSeleccionado by remember {mutableStateOf("Selecciona un animal")}
    var idAnimalSeleccionado by remember {mutableStateOf<Int?>(null)}


    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = Background,

        icon = {
            Icon(
                painter = painterResource(R.drawable.agregar),
                tint = redText,
                contentDescription = "Agregar"
            )
        },

        title = { Text(
            if(raza != null){
                "Agregar raza"
            }else{
                "Agregar animal"
            },
            color = redText
            )},
        text = {
            Column {
                TextField(
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = BackgroundCard2,
                        focusedContainerColor = BackgroundCard2,
                        cursorColor = redText,
                        focusedIndicatorColor = redText
                    ),
                    textStyle = TextStyle(
                        color = textColorsubMain
                    ),
                    value = nombre,
                    onValueChange = {
                        nombre = it.filter {
                            it.isLetter() || it.isWhitespace()
                        }
                                    },
                    label = {
                        if(raza == null){
                            Text("Nombre del animal",
                                color = redText
                            )
                        } else{
                            Text("Nombre de la raza",
                                color = redText)
                        }
                    }
                )

                if(raza != null){
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Animal asociado: ",
                        color = redText)

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        // Campo que muestra el seleccionado
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

                        // Items desplegables
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            viewModel.animales.value.forEach { animal ->
                                DropdownMenuItem(
                                    text = { Text(animal.nombre) },
                                    onClick = {
                                        animalSeleccionado = animal.nombre
                                        idAnimalSeleccionado = animal.id_animal
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
                    if(raza != null && idAnimalSeleccionado == null ){
                        return@TextButton
                    }
                    if(nombre.isEmpty() || nombre.isBlank()){
                        return@TextButton
                    }
                    onConfirmation(nombre, idAnimalSeleccionado)
                }
            ) {
                Text("Aceptar",
                    color = redText)
            }
        },

        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar",
                    color = redText)
            }
        }

    )
}

