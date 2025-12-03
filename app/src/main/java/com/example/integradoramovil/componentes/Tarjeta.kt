package com.example.integradoramovil.componentes

import android.R
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.BackgroundCard
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import kotlinx.coroutines.launch


// tarjeta de raza
@Composable
fun tarjeta(raza: Raza, animal: Animal, viewModel: AnimalRazaUserViewModel) {
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ){
        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text("Nombre: ${raza.nombre}")
                Text("Estado: ${raza.visibilidad}")
                Text("Animal: ${animal.nombre}")
            }

            Row{
                // Editar
                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.integradoramovil.R.drawable.editar),
                        contentDescription = "Editar"
                    )
                }

                // Cambiar estado
                IconButton(
                    onClick = {
                        viewModel.viewModelScope.launch {

                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.integradoramovil.R.drawable.cambiar_estado),
                        contentDescription = "Editar"
                    )
                }

                // Eliminar
                IconButton(
                    onClick = {
                        viewModel.viewModelScope.launch {

                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.integradoramovil.R.drawable.borrar),
                        contentDescription = "Editar"
                    )
                }
            }
        }
    }
}


// tarjeta de animal
@Composable
fun tarjeta(animal: Animal, viewModel: AnimalRazaUserViewModel){
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ){
        LazyRow (
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            items(1){
                Column{
                    Text("Nombre: ${animal.nombre}")
                    Text("Estado: ${animal.visibilidad}")

                    Row{
                        // Editar
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                painter = painterResource(id = com.example.integradoramovil.R.drawable.editar),
                                contentDescription = "Editar"
                            )
                        }

                        // Cambiar estado
                        IconButton(
                            onClick = {
                                viewModel.viewModelScope.launch {
                                    viewModel.cambiarEstado(animal)
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = com.example.integradoramovil.R.drawable.cambiar_estado),
                                contentDescription = "Editar"
                            )
                        }

                        // Eliminar
                        IconButton(
                            onClick = {
                                viewModel.viewModelScope.launch {
                                    println("click en eliminar")
                                    viewModel.eliminarAnimal(animal)
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = com.example.integradoramovil.R.drawable.borrar),
                                contentDescription = "Editar"
                            )
                        }


                    }

                }
            }

        }
    }
}

@Preview
@Composable
fun preview(){
    val a = Animal(1, "hola", "visible")
    val viewModel: AnimalRazaUserViewModel = viewModel()
    tarjeta(a, viewModel)
}