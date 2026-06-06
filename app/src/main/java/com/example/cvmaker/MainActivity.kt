package com.example.cvmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cvmaker.ui.theme.CVMakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CVMakerTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController = navController) }
        composable("login") { LoginScreen(navController = navController) }
        composable("step1") { PersonalInfoStepScreen(navController = navController) }
        composable("step2") { EducationalStepScreen(navController = navController) }
        composable("step3") { SkillsStepScreen(navController = navController) }
        composable("step4"){ AdditionalStepScreen(navController = navController)}
        composable("dashboard") { DashboardScreen(navController = navController) }
    }
}