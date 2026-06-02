package com.example.cvbuilder.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cvbuilder.R
import com.example.cvbuilder.navigation.Screen
import com.example.cvbuilder.ui.components.CustomButton
import com.example.cvbuilder.ui.components.CustomTextField

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.login), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(value = email, onValueChange = { email = it }, label = stringResource(R.string.email))
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = password, onValueChange = { password = it }, label = stringResource(R.string.password))

        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(text = stringResource(R.string.login), onClick = {
            navController.navigate(Screen.Dashboard.route)
        }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.dont_have_account),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { navController.navigate(Screen.SignUp.route) }
        )
    }
}

@Composable
fun SignUpScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
    val passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}\$".toRegex()

    val isEmailValid = email.isEmpty() || email.matches(emailRegex)
    val isPasswordValid = password.isEmpty() || password.matches(passwordRegex)
    val isConfirmValid = confirmPassword.isEmpty() || password == confirmPassword

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.signup), style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(R.string.email),
            isError = !isEmailValid,
            supportingText = if (!isEmailValid) stringResource(R.string.invalid_email) else null
        )
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.password),
            isError = !isPasswordValid,
            supportingText = if (!isPasswordValid) stringResource(R.string.password_weak) else null
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = stringResource(R.string.confirm_password),
            isError = !isConfirmValid,
            supportingText = if (!isConfirmValid) stringResource(R.string.passwords_do_not_match) else null
        )

        Spacer(modifier = Modifier.height(32.dp))
        CustomButton(
            text = stringResource(R.string.signup),
            enabled = isEmailValid && isPasswordValid && isConfirmValid && email.isNotEmpty() && password.isNotEmpty(),
            onClick = { navController.navigate(Screen.Dashboard.route) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.already_have_account),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { navController.popBackStack() }
        )
    }
}