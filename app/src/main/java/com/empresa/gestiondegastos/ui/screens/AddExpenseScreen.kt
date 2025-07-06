package com.empresa.gestiondegastos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onExpenseAdded: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Alimentación") }
    var date by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "Alimentación", "Transporte", "Entretenimiento",
        "Servicios", "Salud", "Educación", "Otros"
    )

    val db = Firebase.firestore

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nuevo Gasto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                        amount = it
                    }
                },
                label = { Text("Monto") },
                leadingIcon = { Icon(Icons.Default.AttachMoney, contentDescription = "Monto") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                leadingIcon = { Icon(Icons.Default.Description, contentDescription = "Descripción") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = !isExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    leadingIcon = { Icon(Icons.Default.Category, contentDescription = "Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                isExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Fecha (ej: 2024-07-06)") },
                leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = "Fecha") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val gasto = hashMapOf(
                        "monto" to amount.toDouble(),
                        "descripcion" to description,
                        "categoria" to selectedCategory,
                        "fecha" to date
                    )

                    db.collection("gastos")
                        .add(gasto)
                        .addOnSuccessListener {
                            onExpenseAdded()
                        }
                        .addOnFailureListener {
                            // Aquí puedes manejar errores si deseas
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = amount.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()
            ) {
                Text("Guardar Gasto")
            }
        }
    }
}
