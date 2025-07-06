package com.empresa.gestiondegastos.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.empresa.gestiondegastos.data.FirebaseService
import android.util.Log
@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onLoginNavigationClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    var visible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        visible = true
    }

    val backgroundColor = Color(0xFFF0F4F8)
    val primaryColor = Color(0xFF006494)
    val secondaryColor = Color(0xFF00A8E8)
    val textFieldBorderColor = Color(0xFFB3CDE0)
    val textColor = Color(0xFF102A43)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(durationMillis = 500)),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(horizontal = 24.dp)
                    .padding(innerPadding), // ya lo tienes
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                AsyncImage(
                    model = "https://cdn-icons-png.flaticon.com/512/3652/3652191.png",
                    contentDescription = "Registro Usuario",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 9.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "REGÍSTRATE",
                    style = MaterialTheme.typography.displaySmall.copy(
                        color = primaryColor,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 2.sp
                    ),
                    modifier = Modifier.padding(bottom = 24.dp),
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(
                            "Nombre",
                            style = TextStyle(color = textColor, fontSize = 15.sp)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = secondaryColor,
                        unfocusedIndicatorColor = textFieldBorderColor
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            "Correo electrónico",
                            style = TextStyle(color = textColor, fontSize = 15.sp)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = secondaryColor,
                        unfocusedIndicatorColor = textFieldBorderColor
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text(
                            "Contraseña",
                            style = TextStyle(color = textColor, fontSize = 15.sp)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = primaryColor
                            )
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = secondaryColor,
                        unfocusedIndicatorColor = textFieldBorderColor
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = {
                        Text(
                            "Confirmar contraseña",
                            style = TextStyle(color = textColor, fontSize = 15.sp)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = primaryColor
                        )
                    },
                    visualTransformation = if (showConfirmPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showConfirmPassword = !showConfirmPassword }) {
                            Icon(
                                imageVector = if (showConfirmPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null,
                                tint = primaryColor
                            )
                        }
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = secondaryColor,
                        unfocusedIndicatorColor = textFieldBorderColor
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )

                Button(
                    onClick = {
                        // Validar campos vacíos
                        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("❌ Todos los campos son obligatorios")
                            }
                            return@Button
                        }

                        // Validar formato de correo electrónico
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("❌ Ingresa un correo electrónico válido")
                            }
                            return@Button
                        }

                        // Validar coincidencia de contraseñas
                        if (password != confirmPassword) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("❌ Las contraseñas no coinciden")
                            }
                            return@Button
                        }

                        // Validar longitud mínima de contraseña
                        if (password.length < 6) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("❌ La contraseña debe tener al menos 6 caracteres")
                            }
                            return@Button
                        }

                        isLoading = true

                        FirebaseService.registerUser(
                            name = name,
                            email = email,
                            password = password,
                            onSuccess = {
                                isLoading = false
                                coroutineScope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "✅ Registro exitoso",
                                        actionLabel = "Ir a login",
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        Log.d("RegisterScreen", "Redirigiendo al Login...") // <-- Confirmación en log
                                        onLoginNavigationClick()
                                    }
                                }
                            },
                            onError = { error ->
                                isLoading = false
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("❌ Error de registro: $error")
                                }
                            }
                        )
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            "Crear cuenta",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }
                }

            }
        }
    }
}
