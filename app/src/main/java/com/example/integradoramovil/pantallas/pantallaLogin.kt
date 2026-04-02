package com.example.integradoramovil.pantallas

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.integradoramovil.R
import com.example.integradoramovil.ui.theme.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.integradoramovil.viewModel.LoginViewModel

@Composable
fun loginFirst(navController: NavController) {
    val RowAnimation = rememberInfiniteTransition()
    val offsetY by RowAnimation.animateFloat(
        initialValue = -10f,
        targetValue = 10f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
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
                        color = Color(0x20000000),
                        offset = DpOffset(x = 4.dp, 4.dp)
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Veterinaria Nairobi",
                style = TextStyle(
                    fontSize = 38.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = baloo,
                    lineHeight = 40.sp,
                ),
                color = TextMain,
                modifier = Modifier.padding(10.dp, 0.dp)
            )
            Text(
                text = "Ellos no pueden hablar, déjanos hablar por ellos",
                style = TextStyle(
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = baloo,
                    lineHeight = 40.sp,
                ),
                color = TextSub,
                modifier = Modifier.padding(1.dp, 0.dp)
            )
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
                modifier = Modifier
                    .size(48.dp)
                    .offset(y = offsetY.dp)
                    .rotate(90f),
                tint = PrimaryOrange
            )
            Text(
                text = "Presioname",
                style = TextStyle(
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = baloo,
                    lineHeight = 40.sp,
                ),
                color = PrimaryOrange,
                modifier = Modifier.padding(1.dp, 0.dp)
            )
        }
    }
}

@Composable
fun LoginSecond(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModel.create(context))
    var correo by rememberSaveable { mutableStateOf("") }
    var contraseña by rememberSaveable { mutableStateOf("") }
    val emailError by viewModel.emailValidationError.collectAsState(initial = null)
    val passwordError by viewModel.passwordValidationError.collectAsState(initial = null)
    val loginError by viewModel.loginError.collectAsState(initial = null)
    val isLoading by viewModel.isLoading.collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 0.dp)
                    .background(BackgroundCard)
                    .zIndex(100f)
                    .align(Alignment.Center)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Inicio de sesión",
                    style = TextStyle(
                        fontSize = 34.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = baloo,
                        lineHeight = 40.sp,
                    ),
                    color = TextMain,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Inicia sesión con los datos que ingresaste durante tu registro",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = baloo,
                        lineHeight = 22.sp,
                    ),
                    color = TextSub,
                    modifier = Modifier.padding(15.dp, 0.dp)
                )

                loginError?.let { error ->
                    Text(
                        text = error,
                        style = TextStyle(fontSize = 14.sp, fontFamily = baloo),
                        modifier = Modifier.padding(15.dp, 0.dp),
                        color = ErrorRed
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        viewModel.validateEmail(it)
                    },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = baloo, fontSize = 16.sp, color = TextMain),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        unfocusedBorderColor = TextSub.copy(alpha = 0.5f),
                        focusedLabelColor = PrimaryOrange,
                        cursorColor = PrimaryOrange
                    ),
                    modifier = Modifier.padding(15.dp, 0.dp).fillMaxWidth()
                )

                emailError?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 12.sp, fontFamily = baloo),
                        color = ErrorRed,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = contraseña,
                    onValueChange = {
                        contraseña = it
                        viewModel.validatePassword(it)
                    },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    textStyle = TextStyle(fontFamily = baloo, fontSize = 16.sp, color = TextMain),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryOrange,
                        unfocusedBorderColor = TextSub.copy(alpha = 0.5f),
                        focusedLabelColor = PrimaryOrange,
                        cursorColor = PrimaryOrange
                    ),
                    modifier = Modifier.padding(15.dp, 0.dp).fillMaxWidth()
                )

                passwordError?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 12.sp, fontFamily = baloo),
                        color = ErrorRed,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )
                }

                Row(
                    modifier = Modifier.padding(15.dp, 5.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Reclamar cuenta", fontSize = 13.sp, color = PrimaryOrange)
                    Text(text = "¿Olvidaste tu contraseña?", fontSize = 13.sp, color = PrimaryOrange)
                }

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = { viewModel.login(correo, contraseña, navController, context) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp).height(50.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
                    } else {
                        Text(text = "Iniciar sesión", style = TextStyle(fontFamily = baloo, fontSize = 18.sp, color = Color.White))
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.padding(15.dp, 0.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "¿No tienes una cuenta?", style = TextStyle(fontFamily = baloo, fontSize = 14.sp, color = TextSub))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Regístrate", style = TextStyle(fontFamily = baloo, fontSize = 14.sp, color = PrimaryOrange, fontWeight = FontWeight.Bold))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            Box(modifier = Modifier.zIndex(1f)) {
                Image(
                    painter = painterResource(R.drawable.huella),
                    contentDescription = "",
                    modifier = Modifier.size(300.dp).offset(150.dp, 80.dp).rotate(45f),
                    alpha = 0.1f
                )
            }
            Box(modifier = Modifier.zIndex(2f)) {
                Image(
                    painter = painterResource(R.drawable.hueso),
                    contentDescription = "",
                    modifier = Modifier.size(210.dp).offset(-40.dp, 80.dp).rotate(5f),
                    alpha = 0.1f
                )
            }
            Box(modifier = Modifier.zIndex(1f)) {
                Image(
                    painter = painterResource(R.drawable.gato),
                    contentDescription = "",
                    modifier = Modifier.size(300.dp).offset(-130.dp, 550.dp).rotate(-15f),
                    alpha = 0.1f
                )
            }
        }
    }
}