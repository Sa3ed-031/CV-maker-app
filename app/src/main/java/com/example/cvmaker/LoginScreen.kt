package com.example.cvmaker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cvmaker.ui.theme.GreenPrimary
import com.example.cvmaker.ui.theme.TextDark
import com.example.cvmaker.ui.theme.CardWhite

import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(navController: NavController) {
    val context= LocalContext.current

    // أحمد هي state لتحديد إذا كنا بlogin أو register
    var isSignUpMode by remember { mutableStateOf(false) }

    // الحقول المشتركة
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    // رسائل الأخطاء (Validation)
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextDark,
        unfocusedTextColor = TextDark,
        focusedLabelColor = GreenPrimary,
        unfocusedLabelColor = TextDark.copy(alpha = 0.6f),
        focusedBorderColor = GreenPrimary,
        unfocusedBorderColor = TextDark.copy(alpha = 0.3f),
        errorTextColor = MaterialTheme.colorScheme.error
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isSignUpMode) stringResource(id=R.string.register_title) else stringResource(id = R.string.login_title),
            color = TextDark,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isSignUpMode) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailError = null },
                label = { Text(stringResource(id=R.string.email_hint)) },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors,
                isError = emailError != null,
                supportingText = {
                    if (emailError != null) {
                        Text(stringResource(id=R.string.email_error))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = username,
            onValueChange = { username = it; usernameError = null },
            label = { Text(stringResource(id = R.string.username_hint)) },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            isError = usernameError != null,
            supportingText = {
                if (usernameError != null) {
                    Text(stringResource(id=R.string.username_error))
                }
            },
            )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; passwordError = null },
            label = { Text(stringResource(id = R.string.password_hint)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors,
            isError = passwordError != null,
            supportingText = {
                if (passwordError != null) {
                    Text(stringResource(id=R.string.pass_error))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val u = username.trim()
                val p = password.trim()
                val e = email.trim()

                if (isSignUpMode) {
                    if (e.isEmpty()) {
                        emailError = "email_error";
                        return@Button
                    }
                    if (u.isEmpty()) {
                        usernameError = "username_error";
                        return@Button
                    }
                    if (p.isEmpty()) {
                        passwordError = "pass_error";
                        return@Button
                    }

                    // إذا عبى البيانات تمام بوضعية التسجيل، ينقله فوراً للخطوة 1 كمستخدم جديد
                    navController.navigate("step1") {
                        popUpTo("login") { inclusive = true }
                    }
                }

                // التحقق بالذاكرة بحالة Login
                else {
                    if (u.isEmpty()) { usernameError = "الرجاء إدخال اسم المستخدم!"; return@Button }
                    if (p.isEmpty()) { passwordError = "الرجاء إدخال كلمة المرور!"; return@Button }

                    if (u == "admin" && p == "123") {
                        // موظف  عالداشبورد
                        navController.navigate("dashboard") { popUpTo("login") { inclusive = true } }
                    } else if (u == "user" && p == "123") {
                        // يوزر مسجل قبل لخطوات السيرة
                        navController.navigate("step1") { popUpTo("login") { inclusive = true } }
                    } else {
                        usernameError = "اسم المستخدم أو كلمة المرور غير صحيحة!"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text(
                text = if (isSignUpMode) stringResource(id=R.string.btn_register) else stringResource(id = R.string.btn_login),
                color = CardWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // زر switch بين الواجهتين (login, signup) بنفس الشاشة
        Text(
            text = if (isSignUpMode) stringResource(id=R.string.switch_btn_register) else stringResource(id=R.string.switch_btn_login),
            color = GreenPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable {
                    isSignUpMode = !isSignUpMode
                    // تصفير الأخطاء والحقول لما ببدل
                    usernameError = null; passwordError = null; emailError = null
                    username = ""; password = ""; email = ""
                }
                .padding(8.dp)
        )
    }
}