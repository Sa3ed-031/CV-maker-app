package com.example.cvbuilder.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cvbuilder.data.models.CVData
import com.example.cvbuilder.ui.screens.*

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.Dashboard.route) { DashboardScreen(navController) }
        composable(Screen.Editor.route) { EditorScreen(navController) }
        composable(Screen.Preview.route) {
            // Retrieve argument from backstack safely
            val cvData = navController.previousBackStackEntry?.savedStateHandle?.get<CVData>("cvData") ?: CVData()
            PreviewScreen(navController, cvData)
        }
    }
}