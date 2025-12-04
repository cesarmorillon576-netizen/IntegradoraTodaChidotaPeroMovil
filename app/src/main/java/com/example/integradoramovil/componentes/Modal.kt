package com.example.integradoramovil.componentes

import androidx.appcompat.widget.DialogTitle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.integradoramovil.R
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.textColor
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

        icon = {
            Icon(
                painter = painterResource(R.drawable.agregar),
                //tint = textColor,
                contentDescription = "Agregar"
            )
        },

        title = { Text(
            if(raza != null){
                "Agregar raza"
            }else{
                "Agregar animal"
            }
            )},
        text = {
            Column {
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = {
                        if(raza == null){
                            Text("Nombre del animal")
                        } else{
                            Text("Nombre de la raza")
                        }
                    }
                )

                if(raza != null){
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text("Animal asociado: ")

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        // Campo que muestra el seleccionado
                        TextField(
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
                    if(raza != null && idAnimalSeleccionado == null){
                        return@TextButton
                    }
                    onConfirmation(nombre, idAnimalSeleccionado)
                }
            ) {
                Text("Aceptar")
            }
        },

        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancelar")
            }
        }

    )
}

@Preview
@Composable
fun la_preview(){

}