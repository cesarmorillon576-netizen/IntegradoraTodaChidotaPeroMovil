package com.example.integradoramovil.componentes

import android.R
import android.R.id.bold
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.modelos.Raza
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.BackgroundCard
import com.example.integradoramovil.ui.theme.BackgroundCard2
import com.example.integradoramovil.ui.theme.dropDownBackground
import com.example.integradoramovil.ui.theme.redText
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textOrange
import com.example.integradoramovil.viewModel.AnimalRazaUserViewModel
import kotlinx.coroutines.launch


// tarjeta de raza
@Composable
fun tarjeta(
    raza: Raza,
    animal: Animal,
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
) {
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if(raza.visibilidad == "visible") BackgroundCard2
            else BackgroundCard
        )
    ){
        Row (
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = if(raza.visibilidad == "visible") dropDownBackground
                            else BackgroundCard2,
                            fontWeight = FontWeight.Bold)) {
                            append("Nombre: ")
                        }

                        withStyle(style = SpanStyle(
                            color = if(raza.visibilidad == "visible") redText
                            else Background)) {
                            append(raza.nombre)
                        }
                    }
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = if(raza.visibilidad == "visible") dropDownBackground
                            else BackgroundCard2,
                            fontWeight = FontWeight.Bold)) {
                            append("Estado: ")
                        }

                        withStyle(style = SpanStyle(
                            color = if(raza.visibilidad == "visible") redText
                            else Background)) {
                            append(raza.visibilidad)
                        }
                    }
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = if(raza.visibilidad == "visible") dropDownBackground
                            else BackgroundCard2,
                            fontWeight = FontWeight.Bold)) {
                            append("Animal: ")
                        }

                        withStyle(style = SpanStyle(
                            color = if(raza.visibilidad == "visible") redText
                            else Background)) {
                            append(animal.nombre)
                        }
                    }
                )
            }

            Row{
                // Editar
                IconButton(
                    onClick = {
                        abrirEditar(raza, animal)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.integradoramovil.R.drawable.editar),
                        contentDescription = "Editar",
                        tint = if(raza.visibilidad == "invisible") textColor
                        else Color.Black
                    )
                }

                // Cambiar estado
                IconButton(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.cambiarEstado(raza)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.integradoramovil.R.drawable.cambiar_estado),
                        contentDescription = "Editar",
                        tint = if(raza.visibilidad == "invisible") textColor
                        else Color.Black
                    )
                }

                // Eliminar
                IconButton(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            println("click en eliminar")
                            viewModel.eliminarRaza(raza)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = com.example.integradoramovil.R.drawable.borrar),
                        contentDescription = "Editar",
                        tint = if(raza.visibilidad == "invisible") textColor
                        else Color.Black
                    )
                }
            }
        }
    }
}


// tarjeta de animal
@Composable
fun tarjeta(
    animal: Animal,
    viewModel: AnimalRazaUserViewModel,
    abrirEditar: (Raza?, Animal?) -> Unit
){
    Card (
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if(animal.visibilidad == "visible") BackgroundCard2
            else BackgroundCard
        )
    ){
        LazyRow (
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            items(1){
                Column{
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                color = if(animal.visibilidad == "visible") dropDownBackground
                                else BackgroundCard2,
                                fontWeight = FontWeight.Bold)) {
                                append("Nombre: ")
                            }

                            withStyle(style = SpanStyle(
                                color = if(animal.visibilidad == "visible") redText
                                else Background)) {
                                append(animal.nombre)
                            }
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                color = if(animal.visibilidad == "visible") dropDownBackground
                                else BackgroundCard2,
                                fontWeight = FontWeight.Bold)) {
                                append("Estado: ")
                            }

                            withStyle(style = SpanStyle(
                                color = if(animal.visibilidad == "visible") redText
                                else Background)) {
                                append(animal.visibilidad)
                            }
                        }
                    )

                    Row{
                        // Editar
                        IconButton(
                            onClick = {
                                abrirEditar(null, animal)
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = com.example.integradoramovil.R.drawable.editar),
                                contentDescription = "Editar",
                                tint = if(animal.visibilidad == "invisible") textColor
                                else Color.Black
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
                                contentDescription = "Cambiar estado",
                                tint = if(animal.visibilidad == "invisible") textColor
                                else Color.Black
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
                                contentDescription = "Borrar",
                                tint = if(animal.visibilidad == "invisible") textColor
                                else Color.Black
                            )
                        }


                    }

                }
            }

        }
    }
}

