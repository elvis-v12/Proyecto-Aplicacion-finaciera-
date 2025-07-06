package com.empresa.gestiondegastos.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.empresa.gestiondegastos.ui.screens.LoginScreen
import com.empresa.gestiondegastos.ui.screens.RegisterScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "register",
        modifier = modifier
    ) {
        composable("register") {
            RegisterScreen(
                onLoginNavigationClick = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onLoginSuccess = {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onForgotPasswordClick = {
                    navController.navigate("forgot_password")
                },
                onGoogleSignInClick = {
                    // Si implementas Google Sign-In más adelante
                    navController.navigate("dashboard")
                }
            )
        }

    }
}
