package com.example.cvbuilder.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Dashboard : Screen("dashboard")
    object Editor : Screen("editor")
    object Preview : Screen("preview")
}