package com.example.integradoramovil.pantallas

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.integradoramovil.R
import com.example.integradoramovil.viewModel.*
import com.example.integradoramovil.ui.theme.Background
import com.example.integradoramovil.ui.theme.BackgroundCard
import com.example.integradoramovil.ui.theme.BackgroundCard2
import com.example.integradoramovil.ui.theme.baloo
import com.example.integradoramovil.ui.theme.buttonColor
import com.example.integradoramovil.ui.theme.redText
import com.example.integradoramovil.ui.theme.textColor
import com.example.integradoramovil.ui.theme.textColorsubMain
import com.example.integradoramovil.ui.theme.textInputOrange
import com.example.integradoramovil.ui.theme.textOrange
import androidx.compose.animation.core.rememberInfiniteTransition
import com.example.integradoramovil.viewModel.LoginViewModel

@Composable
fun loginFirst( navController: NavController){
    val RowAnimation = rememberInfiniteTransition()
    val offsetY by RowAnimation.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp)
                .background(BackgroundCard)
                .height(600.dp)
                .dropShadow(
                    shape = RoundedCornerShape(20.dp),
                    shadow = Shadow(
                        radius = 10.dp,
                        spread = 6.dp,
                        color = Color(0x40000000),
                        offset = DpOffset(x = 4.dp, 4.dp)
                    )
                )
            , horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Veterinaria Nairobi", style = TextStyle(
                fontSize = 38.sp,
                textAlign = TextAlign.Center,
                fontFamily = baloo,
                lineHeight = 40.sp,
            ), color = textColor, modifier = Modifier.padding(
                10.dp,0.dp
            ))
            Text(text = "Ellos no pueden hablar, déjanos hablar por ellos ", style = TextStyle(
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                fontFamily = baloo,
                lineHeight = 40.sp,
            ), color = textColor, modifier = Modifier.padding(
                1.dp,0.dp
            ))
            Image(
                painter = painterResource(R.drawable.huella),
                contentDescription = "Garra",
                modifier = Modifier
                    .size(170.dp)
                    .rotate(60f)
                    .clickable {
                        navController.navigate("loginSecond")
                    }
            )
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Estrella",
                modifier = Modifier.size(48.dp)
                    .offset( y = offsetY.dp).rotate(90f),
                tint = textColor
            )
            Text(text = "Presioname", style = TextStyle(
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                fontFamily = baloo,
                lineHeight = 40.sp,
            ), color = textColor, modifier = Modifier.padding(
                1.dp,0.dp
            ))

        }
    }
}
@Composable
fun LoginSecond(navController: NavController,viewModel: LoginViewModel = LoginViewModel){
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)

        ){
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
                    .background(BackgroundCard2)
                    .zIndex(100f)
                    .align( Alignment.Center)
                /* .dropShadow(
                     shape = RoundedCornerShape(10.dp),
                     shadow = Shadow(
                         radius = 10.dp,
                         spread = 6.dp,
                         color = Color(0x40000000),
                         offset = DpOffset(x = 1.dp, 1.dp)
                     )
                 )*/
                ,
                //horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            )
            {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = "Inicio de sesión",
                    style = TextStyle(
                        fontSize = 34.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = baloo,
                        lineHeight = 40.sp,

                        ), color = textColorsubMain,
                    modifier = Modifier.fillMaxWidth())
                Text(text = "Inicia sesión con los datos que ingresaste durante tu registro",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = baloo,
                        lineHeight = 20.sp,
                    ), color = textOrange,
                    modifier = Modifier.padding(15.dp,0.dp))
                if(!viewModel.IsText.isEmpty()){
                    Text(text = viewModel.IsText,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = baloo,
                            lineHeight = 15.sp
                        ),modifier = Modifier.padding(15.dp,0.dp),
                        color = redText)
                }
                Text(text = "Ingresa tu correo electrónico o número telefónico",
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = baloo,
                        lineHeight = 15.sp,
                    ), color = textColorsubMain,
                    modifier = Modifier.padding(15.dp,0.dp))
                if(!viewModel.isNotEmail.isNullOrEmpty()){
                    Text(text = viewModel.isNotEmail,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = baloo,
                            lineHeight = 15.sp,
                        ), color = redText,
                        modifier = Modifier.padding(15.dp,0.dp)
                    )
                }
                OutlinedTextField(
                    value = correo,
                    onValueChange = {correo = it},
                    singleLine = true,
                    placeholder = {Text(text = "Nombre@ejemplo.com",
                        color = BackgroundCard)},
                    textStyle = TextStyle(
                        fontFamily = baloo,
                        fontSize = 16.sp,
                        color = textInputOrange
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF583500),
                        unfocusedBorderColor = BackgroundCard,
                        cursorColor = Color(0xFF583500),
                        focusedLabelColor = Color(0xFF583500),
                        unfocusedLabelColor = BackgroundCard,
                        focusedTextColor = BackgroundCard,
                        unfocusedTextColor = BackgroundCard
                    ),
                    modifier = Modifier
                        .padding(15.dp,0.dp)
                        .fillMaxWidth()


                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Ingresa tu contraseña",
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = baloo,
                        lineHeight = 15.sp,
                    ), color = textColorsubMain,
                    modifier = Modifier.padding(15.dp,0.dp)
                )
                if(!viewModel.isNotPassword.isNullOrEmpty()){
                    Text(text = viewModel.isNotPassword,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = baloo,
                            lineHeight = 15.sp,
                        ), color = redText,
                        modifier = Modifier.padding(15.dp,0.dp)
                    )
                }

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = {contraseña = it},
                    singleLine = true,
                    placeholder = {Text(text = "****",
                        color = BackgroundCard)},
                    textStyle = TextStyle(
                        fontFamily = baloo,
                        fontSize = 14.sp,
                        color = textInputOrange
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF583500),
                        unfocusedBorderColor = BackgroundCard,
                        cursorColor = Color(0xFF583500),
                        focusedLabelColor = Color(0xFF583500),
                        unfocusedLabelColor = BackgroundCard,
                        focusedTextColor = BackgroundCard,
                        unfocusedTextColor = BackgroundCard
                    ),
                    modifier = Modifier
                        .padding(15.dp,0.dp)
                        .fillMaxWidth()


                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier
                        .padding(15.dp,0.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "Reclamar cuenta",
                        fontSize = 14.sp,
                        color = redText
                    )
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        fontSize = 14.sp,
                        color = redText
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(onClick = {
                    viewModel.IsLoading = true
                    Log.e("BOTON", "El botón sí fue clickeado")
                    viewModel.trying(correo, contraseña, navController)},
                    shape = RoundedCornerShape(3.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonColor,
                    ), modifier = Modifier.fillMaxWidth().padding(40.dp,0.dp)){
                    if(viewModel.IsLoading){
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth =2.dp
                        )
                    }else{
                        Text(text="Iniciar sesión",
                            style= TextStyle(
                                fontFamily = baloo,
                                fontSize = 17.sp,
                                color = Color.White
                            ))
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier =Modifier.padding(15.dp,0.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(text="¿No tienes una cuenta?",
                        style= TextStyle(
                            fontFamily = baloo,
                            fontSize = 14.sp,
                            color = textColorsubMain
                        ),modifier = Modifier.padding(0.dp,0.dp,5.dp,0.dp))
                    Text(text="Regístrate",
                        style= TextStyle(
                            fontFamily = baloo,
                            fontSize = 14.sp,
                            color = redText
                        ))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
            Box(
                modifier = Modifier.zIndex(1f)
            )
            {
                Image(
                    painter = painterResource(R.drawable.huella),
                    contentDescription = "",
                    modifier = Modifier.size(300.dp)
                        .offset(150.dp,80.dp)
                        .rotate(45f)
                )
            }
            Box(
                modifier = Modifier.zIndex(2f)
            )
            {
                Image(
                    painter = painterResource(R.drawable.hueso),
                    contentDescription = "",
                    modifier = Modifier.size(210.dp)
                        .offset(-40.dp,80.dp)
                        .rotate(5f)
                )
            }
            Box(
                modifier = Modifier.zIndex(1f)
            )
            {
                Image(
                    painter = painterResource(R.drawable.gato),
                    contentDescription = "",
                    modifier = Modifier.size(300.dp)
                        .offset(-130.dp,550.dp)
                        .rotate(-15f)
                )
            }
        }


    }

}