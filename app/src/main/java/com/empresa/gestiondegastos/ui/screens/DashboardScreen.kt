package com.empresa.gestiondegastos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.*
import androidx.compose.foundation.clickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onAddExpense: () -> Unit,
    onViewHistory: () -> Unit,
    onViewBudget: () -> Unit,
    onViewAlerts: () -> Unit,
    onExportData: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedPeriod by remember { mutableStateOf("Este mes") }
    val periods = listOf("Hoy", "Esta semana", "Este mes", "Este año")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Resumen de gastos
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Gastos del mes",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$1,234.56",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = Icons.Default.Add,
                    text = "Agregar Gasto",
                    onClick = onAddExpense
                )
                ActionButton(
                    icon = Icons.Default.History,
                    text = "Historial",
                    onClick = onViewHistory
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = Icons.Default.AccountBalance,
                    text = "Presupuesto",
                    onClick = onViewBudget
                )
                ActionButton(
                    icon = Icons.Default.Notifications,
                    text = "Alertas",
                    onClick = onViewAlerts
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionButton(
                    icon = Icons.Default.FileDownload,
                    text = "Exportar",
                    onClick = onExportData
                )
            }
        }
    }
}

@Composable
fun CategoryCard(category: ExpenseCategory) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = category.name,
                    tint = category.color
                )
                Column {
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formatCurrency(category.amount),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Text(
                text = "${category.percentage}%",
                style = MaterialTheme.typography.titleMedium,
                color = category.color
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text)
    }
}

data class ExpenseCategory(
    val name: String,
    val amount: Double,
    val percentage: Int,
    val icon: ImageVector,
    val color: Color
)

fun getExpenseCategories(): List<ExpenseCategory> = listOf(
    ExpenseCategory(
        "Alimentación",
        450.00,
        36,
        Icons.Default.Restaurant,
        Color(0xFF4CAF50)
    ),
    ExpenseCategory(
        "Transporte",
        300.00,
        24,
        Icons.Default.DirectionsCar,
        Color(0xFF2196F3)
    ),
    ExpenseCategory(
        "Entretenimiento",
        200.00,
        16,
        Icons.Default.Movie,
        Color(0xFF9C27B0)
    ),
    ExpenseCategory(
        "Servicios",
        300.50,
        24,
        Icons.Default.Home,
        Color(0xFFFF9800)
    )
)

fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
    return format.format(amount)
} 