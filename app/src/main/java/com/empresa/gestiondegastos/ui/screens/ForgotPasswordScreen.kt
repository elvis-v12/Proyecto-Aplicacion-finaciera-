package com.empresa.gestiondegastos.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ForgotPasswordScreen(
    onSendResetEmail: (String) -> Unit = { },
    onBackToLogin: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF0F4F8)
    val primaryColor = Color(0xFF006494)
    val secondaryColor = Color(0xFF00A8E8)
    val textColor = Color(0xFF102A43)
    val textFieldBorderColor = Color(0xFFB3CDE0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Imagen desde URL
        AsyncImage(
            model = "https://cdn-icons-png.flaticon.com/512/3062/3062634.png",
            contentDescription = "Recover Password Illustration",
            modifier = Modifier
                .size(180.dp)
                .padding(bottom = 16.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Recuperar Contraseña",
            style = MaterialTheme.typography.displaySmall.copy(
                color = primaryColor,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            ),
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Ingresa tu correo electrónico para enviarte un enlace de recuperación.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = textColor,
                fontSize = 15.sp
            ),
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Correo electrónico", style = TextStyle(color = textColor, fontSize = 15.sp))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = primaryColor
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = textFieldBorderColor,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        if (showSuccessMessage) {
            Text(
                text = "¡Correo enviado exitosamente!",
                color = Color(0xFF4CAF50),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        Button(
            onClick = {
                if (!isLoading) {
                    isLoading = true
                }
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
                    "Enviar enlace",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onBackToLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Volver al inicio de sesión",
                color = primaryColor,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            )
        }
    }

    if (isLoading) {
        LaunchedEffect(isLoading) {
            kotlinx.coroutines.delay(2000)
            isLoading = false
            onSendResetEmail(email)
            showSuccessMessage = true
        }
    }
}