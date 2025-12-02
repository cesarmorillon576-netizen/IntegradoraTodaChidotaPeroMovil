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
import com.example.integradoramovil.modelos.Animal
import com.example.integradoramovil.ui.theme.BackgroundCard

@Composable
fun tarjeta(nombre: String, visibilidad: String, animal: Animal?){
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
                Text("Nombre: ${nombre}")
                Text("Estado: ${visibilidad}")
                Text("Animal: ${animal?.nombre}")
            }

            Column {
                Button(
                    onClick = {}
                ) {
                    Text("hola")
                }
                IconButton(
                    modifier = Modifier,
                    onClick = {}
                ) {
                    /*
                    Icon(
                        painter = painterResource()
                    )*/
                }
            }
        }
    }
}

@Composable
fun tarjeta(nombre: String, visibilidad: String){
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
                    Text("Nombre: ${nombre}")
                    Text("Estado: ${visibilidad}")

                    Row{
                        Button(
                            onClick = {}
                        ) {
                            Text("hola")
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
    tarjeta("hola", "si")
}