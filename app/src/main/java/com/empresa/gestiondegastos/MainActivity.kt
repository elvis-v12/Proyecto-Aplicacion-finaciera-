package com.empresa.gestiondegastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.empresa.gestiondegastos.ui.theme.GestionDeGastosTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.empresa.gestiondegastos.ui.screens.LoginScreen
import com.empresa.gestiondegastos.ui.screens.RegisterScreen
import com.empresa.gestiondegastos.ui.screens.ForgotPasswordScreen
import com.empresa.gestiondegastos.ui.screens.DashboardScreen
import com.empresa.gestiondegastos.ui.screens.AddExpenseScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionDeGastosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome",
        modifier = modifier
    ) {
        composable("welcome") {
            GreetingScreen(
                modifier = Modifier,
                onLoginClick = {
                    navController.navigate("login")
                },
                onStartClick = {
                    navController.navigate("register")
                }
            )
        }
        composable("login") {
            LoginScreen(
                onLoginClick = { email, password ->
                    // Aquí irá la lógica de login
                    navController.navigate("dashboard")
                },
                onGoogleSignInClick = {
                    // Aquí irá la lógica de Google Sign-In
                    navController.navigate("dashboard")
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterClick = { name, email, password, confirmPassword ->
                    // Aquí irá la lógica de registro
                    navController.navigate("login")
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onSendResetEmail = { email ->
                    // Aquí irá la lógica para enviar el correo de recuperación
                },
                onBackToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onAddExpense = {
                    navController.navigate("add_expense")
                },
                onViewHistory = {
                    // Ir a la pantalla de historial
                },
                onViewBudget = {
                    // Ir a la pantalla de presupuesto
                },
                onViewAlerts = {
                    // Ir a la pantalla de alertas
                },
                onExportData = {
                    // Ir a la pantalla de exportación
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                }
            )
        }
        composable("add_expense") {
            AddExpenseScreen(
                onExpenseAdded = {
                    navController.navigate("dashboard") {
                        popUpTo("add_expense") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun GreetingScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onStartClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🟢 Título principal
        Text(
            text = "Una forma simple de llevar el control de tus gastos.",
            fontSize = 25.sp,
            color = Color.Black,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)

        )

        // 🔄 Aquí irá el carrusel más adelante
        val images = listOf(
            "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
            "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
            "https://images.unsplash.com/photo-1602524207237-14b6d8d1e4af?w=1080"
        )

        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        HorizontalPager(
            count = images.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = rememberAsyncImagePainter(images[page]),
                    contentDescription = "Imagen del carrusel",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 👋 Texto de bienvenida
        Text(
            text = "Hola Bienvenido!",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // ▶️ Botón "Empezar"
        Button(
            onClick = onStartClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Empezar")
        }

        // 🔤 Texto "Ya me registré, entrar"
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Ya me registré, entrar",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            modifier = Modifier
                .clickable(onClick = onLoginClick)
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GestionDeGastosTheme {
        GreetingScreen()
    }
}
