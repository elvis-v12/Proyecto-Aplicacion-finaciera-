@file:OptIn(ExperimentalMaterial3Api::class)

package com.empresa.gestiondegastos.ui.screens

// Layout y estilo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

// Material Icons y Material 3
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.*

// Alineación y colores
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

// Para TextField (teclado, contraseña, etc.)
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

// Otros
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign

@Composable
fun WalletScreen(onBack: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var tarjetaGuardada by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cartera") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (tarjetaGuardada) {
                TarjetaSimulada(nombre, numero, fecha)
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { tarjetaGuardada = false }) {
                    Text("Editar Tarjeta")
                }
            } else {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del titular") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número de tarjeta") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = fecha,
                    onValueChange = { fecha = it },
                    label = { Text("Fecha de vencimiento (MM/AA)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        tarjetaGuardada = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar tarjeta")
                }
            }
        }
    }
}

@Composable
fun TarjetaSimulada(nombre: String, numero: String, fecha: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF1565C0), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "💳 Tarjeta DevBlu",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = numero.chunked(4).joinToString(" "),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Titular: $nombre", color = Color.White)
                Text("Vence: $fecha", color = Color.White)
            }
        }
    }
}
