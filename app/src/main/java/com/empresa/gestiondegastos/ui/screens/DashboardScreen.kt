package com.empresa.gestiondegastos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.empresa.gestiondegastos.R
import kotlinx.datetime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onAddExpense: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToCharts: () -> Unit,
    onNavigateToWallet: () -> Unit,
    onViewHistory: () -> Unit,
    onViewBudget: () -> Unit,
    onViewAlerts: () -> Unit,
    onExportData: () -> Unit
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var selectedMonth by remember { mutableStateOf(today.monthNumber) }
    var selectedYear by remember { mutableStateOf(today.year) }
    val months = (1..12).toList()
    val weeks = getWeeksOfMonth(selectedYear, selectedMonth)
    val summary = getFakeFinancialData(selectedMonth, selectedYear)

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExpense, containerColor = Color(0xFF00A8E8)) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        },
        bottomBar = {
            BottomNavigationBar(
                onNavigateToCalendar = onNavigateToCalendar,
                onNavigateToCharts = onNavigateToCharts,
                onNavigateToWallet = onNavigateToWallet
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F4F8))
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // CABECERA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFB2FEFA), Color(0xFF0ED2F7), Color(0xFFE0C3FC)
                            )
                        )
                    )
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Fila superior
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE57373)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("E", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { /* Buscar */ }) {
                                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF006494))
                            }
                            IconButton(onClick = onLogout) {
                                Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión", tint = Color(0xFF006494))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Selector de mes
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        months.forEach { month ->
                            val selected = month == selectedMonth
                            val label = monthNumberToShortName(month).uppercase()
                            Text(
                                text = label,
                                color = if (selected) Color.White else Color(0xFF006494),
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = if (selected) 18.sp else 16.sp,
                                modifier = Modifier
                                    .background(
                                        if (selected) Color(0xFF00A8E8) else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                    .clickable { selectedMonth = month }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${monthNumberToShortName(selectedMonth).replaceFirstChar { it.uppercase() }} $selectedYear Saldo",
                        color = Color(0xFF006494),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${summary.ingresos - summary.gastos} €",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Card(
                            modifier = Modifier.weight(1f).padding(4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.TrendingUp, contentDescription = null, tint = Color(0xFF00A86B))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Ingresos", color = Color(0xFF00A86B), fontWeight = FontWeight.Bold)
                                }
                                Text("${summary.ingresos} €", fontWeight = FontWeight.Bold)
                            }
                        }
                        Card(
                            modifier = Modifier.weight(1f).padding(4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.TrendingDown, contentDescription = null, tint = Color(0xFFD7263D))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Gastos", color = Color(0xFFD7263D), fontWeight = FontWeight.Bold)
                                }
                                Text("${summary.gastos} €", fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Tarjeta de crédito simulada
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E88E5))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Tarjeta de Crédito", color = Color.White, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("**** **** **** 1234", color = Color.White)
                            Text("Saldo disponible: S/ ${summary.ingresos - summary.gastos}", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                weeks.forEach { week ->
                    Text(
                        text = week,
                        fontSize = 16.sp,
                        color = Color(0xFF102A43),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.finance_illustration),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(120.dp)
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    onNavigateToCalendar: () -> Unit,
    onNavigateToCharts: () -> Unit,
    onNavigateToWallet: () -> Unit
) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = onNavigateToCalendar,
            icon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) },
            label = { Text("Calendario") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onNavigateToCharts,
            icon = { Icon(Icons.Default.PieChart, contentDescription = null) },
            label = { Text("Gráficos") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onNavigateToWallet,
            icon = { Icon(Icons.Default.AccountBalanceWallet, contentDescription = null) },
            label = { Text("Cartera") }
        )
    }
}

// ----------- FUNCIONES DE UTILIDAD -----------

data class FinancialSummary(
    val ingresos: Float,
    val gastos: Float
)

fun getFakeFinancialData(month: Int, year: Int): FinancialSummary {
    return when (month) {
        1 -> FinancialSummary(ingresos = 1200f, gastos = 800f)
        2 -> FinancialSummary(ingresos = 1000f, gastos = 950f)
        3 -> FinancialSummary(ingresos = 1400f, gastos = 1100f)
        else -> FinancialSummary(ingresos = 800f + month * 50, gastos = 700f + month * 30)
    }
}

fun getWeeksOfMonth(year: Int, month: Int): List<String> {
    val firstDay = LocalDate(year, month, 1)
    val lastDay = LocalDate(year, month, daysInMonth(year, month))
    val weeks = mutableListOf<String>()
    var current = firstDay
    while (current <= lastDay) {
        val weekStart = current
        val weekEnd = minOf(current.plus(DatePeriod(days = 6)), lastDay)
        val label = "${weekStart.dayOfMonth.toString().padStart(2, '0')} ${monthNumberToShortName(weekStart.monthNumber)} - ${weekEnd.dayOfMonth.toString().padStart(2, '0')} ${monthNumberToShortName(weekEnd.monthNumber)}"
        weeks.add(label)
        current = weekEnd.plus(DatePeriod(days = 1))
    }
    return weeks
}

fun daysInMonth(year: Int, month: Int): Int =
    when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> 30
    }

fun isLeapYear(year: Int): Boolean =
    (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)

fun monthNumberToShortName(month: Int): String =
    listOf("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic")[month - 1]
