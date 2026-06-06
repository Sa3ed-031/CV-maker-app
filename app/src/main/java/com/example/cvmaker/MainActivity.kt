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

    // 🔄 تهيئة نسخة الـ ViewModel المشتركة لكل الخطوات هنا
    val cvViewModel: CvViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(navController = navController, startDestination = "splash") {
        composable(route = "splash") { SplashScreen(navController = navController) }
        composable(route = "login") { LoginScreen(navController = navController) }

        // تمرير الـ cvViewModel للشاشات الأربعة ليحفظوا البيانات الحقيقية جواته
        composable(route = "step1") { PersonalInfoStepScreen(navController = navController, viewModel = cvViewModel) }
        composable(route = "step2") { EducationalStepScreen(navController = navController, viewModel = cvViewModel) }
        composable(route = "step3") { SkillsStepScreen(navController = navController, viewModel = cvViewModel) }
        composable(route = "step4") { AdditionalStepScreen(navController = navController, viewModel = cvViewModel) }

        // 👁️ إضافة الراوت الناقص لشاشة المراجعة وتمرير نفس الـ ViewModel لقراءة البيانات الفعلية
        composable(route = "cv_preview") { CvPreviewScreen(navController = navController, viewModel = cvViewModel) }
    }
}