package com.empresa.gestiondegastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.empresa.gestiondegastos.ui.screens.*
import com.empresa.gestiondegastos.ui.theme.GestionDeGastosTheme
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
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
                onLoginClick = { navController.navigate("login") },
                onStartClick = { navController.navigate("register") }
            )
        }
        composable("login") {
            LoginScreen(
                onRegisterClick = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoogleSignInClick = { navController.navigate("dashboard") },
                onForgotPasswordClick = { navController.navigate("forgot_password") },
                onSocialClick = {},
                onContinueWithoutAccount = {}
            )
        }
        composable("register") {
            RegisterScreen(
                onLoginNavigationClick = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }
        composable("forgot_password") {
            ForgotPasswordScreen(
                onSendResetEmail = { email -> },
                onBackToLogin = { navController.navigate("login") }
            )
        }
        composable("dashboard") {
            DashboardScreen(
                onAddExpense = { navController.navigate("add_expense") },
                onViewHistory = { /* implementa si deseas */ },
                onViewBudget = { /* implementa si deseas */ },
                onViewAlerts = { /* implementa si deseas */ },
                onExportData = { /* implementa si deseas */ },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("dashboard") { inclusive = true }
                    }
                },
                onNavigateToCalendar = {
                    // por ahora puede redirigir al mismo dashboard
                    navController.navigate("dashboard")
                },
                onNavigateToCharts = {
                    navController.navigate("charts")
                },
                onNavigateToWallet = {
                    navController.navigate("wallet")
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
                onBack = { navController.popBackStack() }
            )
        }
        composable("charts") {
            ChartsScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable("wallet") {
            WalletScreen(
                onBack = { navController.popBackStack() }
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
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Una forma simple de llevar el control de tus gastos.",
            fontSize = 25.sp,
            color = Color.Black,
            lineHeight = 36.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
        )

        val images = listOf(
            R.drawable.primera1,
            R.drawable.segunda2,
            R.drawable.tercera3
        )


        val pagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    count = images.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
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

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .padding(16.dp),
                    activeColor = MaterialTheme.colorScheme.primary,
                    inactiveColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Empezar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = "¿Ya tienes una cuenta?",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.clickable(onClick = onLoginClick)
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GestionDeGastosTheme {
        GreetingScreen()
    }
}
