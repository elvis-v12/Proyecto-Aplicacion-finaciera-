package com.empresa.gestiondegastos.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import java.util.*

@Composable
fun generateFakeFinanceData(): Pair<List<Float>, List<Float>> {
    val ingresos = List(12) { (500..1500).random().toFloat() }
    val gastos = List(12) { (300..1400).random().toFloat() }
    return Pair(ingresos, gastos)
}

@Composable
fun CreditCardSimulada(balance: Float = 1250.75f) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2A78)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Tarjeta de crédito", color = Color.White, fontSize = 18.sp)
            Spacer(Modifier.height(12.dp))
            Text("**** **** **** 1234", color = Color.White, fontSize = 20.sp)
            Spacer(Modifier.height(8.dp))
            Text("Saldo disponible", color = Color(0xFFB3CFFF), fontSize = 14.sp)
            Text("S/. %.2f".format(balance), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FinanceBarChart(ingresos: List<Float>, gastos: List<Float>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Gráfico de Ingresos y Gastos", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(12.dp))
        ingresos.indices.forEach { i ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = monthNumberToShortName(i + 1),
                    modifier = Modifier.width(40.dp),
                    fontSize = 12.sp
                )
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .weight(ingresos[i] / 2000)
                        .clip(RectangleShape)
                        .background(Color(0xFF00A86B))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .weight(gastos[i] / 2000)
                        .clip(RectangleShape)
                        .background(Color(0xFFD7263D))
                )
            }
        }
    }
}

fun monthNumberToShortName(month: Int): String {
    return when (month) {
        1 -> "Ene"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Abr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Ago"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dic"
        else -> "?"
    }
}
