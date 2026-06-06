package com.example.cvmaker // تأكد إنو نفس اسم الباكج تبعك

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cvmaker.ui.theme.GreenDark
import com.example.cvmaker.ui.theme.GreenSecondary
import com.example.cvmaker.ui.theme.CardWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    // هاد التابع بيعمل تأخير ثانيتين ونص بعدين بينتقل تلقائياً لشاشة الlogin
    LaunchedEffect(key1 = true) {
        delay(2500)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenDark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                color = CardWhite,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(24.dp))
            // مؤشر التحميل الفاتح
            CircularProgressIndicator(
                color = GreenSecondary,
                strokeWidth = 4.dp
            )
        }
    }
}