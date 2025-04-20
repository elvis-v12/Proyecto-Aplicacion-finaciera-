package com.empresa.gestiondegastos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ForgotPasswordScreen(
    onSendResetEmail: (String) -> Unit = { },
    onBackToLogin: () -> Unit = { }
) {
    var email by remember { mutableStateOf("") }
    var showSuccessMessage by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Controlia",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 48.dp, bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Recuperar contraseña",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Ingresa tu correo electrónico y te enviaremos un enlace para restablecer tu contraseña.",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Ingresa tu correo electrónico") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )

        if (showSuccessMessage) {
            Text(
                text = "Se ha enviado un correo electrónico con las instrucciones para restablecer tu contraseña.",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = {
                onSendResetEmail(email)
                showSuccessMessage = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Enviar enlace")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onBackToLogin,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Volver al inicio de sesión")
        }
    }
} 