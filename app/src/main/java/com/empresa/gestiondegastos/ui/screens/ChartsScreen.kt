package com.empresa.gestiondegastos.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class) // ✅ Esto elimina el warning del TopAppBar
@Composable
fun ChartsScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gráficos") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            BarChart(
                ingresos = listOf(200f, 300f, 250f),  // Datos simulados
                gastos = listOf(120f, 180f, 90f),
                labels = listOf("Mayo", "Junio", "Julio")
            )
        }
    }
}

@Composable
fun BarChart(ingresos: List<Float>, gastos: List<Float>, labels: List<String>) {
    val maxValor = (ingresos + gastos).maxOrNull() ?: 1f  // ✅ para evitar división por 0
    val barWidth = 30.dp
    val spacing = 32.dp

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Ingresos vs Gastos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            ingresos.indices.forEach { i ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Barra de ingresos
                    Canvas(
                        modifier = Modifier
                            .width(barWidth)
                            .height((ingresos[i] / maxValor * 180).dp)
                    ) {
                        drawRect(Color(0xFF4CAF50)) // Verde
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Barra de gastos
                    Canvas(
                        modifier = Modifier
                            .width(barWidth)
                            .height((gastos[i] / maxValor * 180).dp)
                    ) {
                        drawRect(Color(0xFFF44336)) // Rojo
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(labels[i], style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("🟩 Ingresos", color = Color(0xFF4CAF50))
            Text("🟥 Gastos", color = Color(0xFFF44336))
        }
    }
}
