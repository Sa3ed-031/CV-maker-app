package com.example.cvbuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.cvbuilder.navigation.NavigationGraph
import com.example.cvbuilder.ui.theme.CVBuilderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // تفعيل الثيم الخاص بالتطبيق (يدعم الـ Dark/Light mode تلقائياً)
            CVBuilderTheme {
                // حاوية السطح الأساسية للتطبيق باستخدام ألوان الـ Material 3
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // إنشاء وحدة التحكم بالملاحة (NavController)
                    val navController = rememberNavController()

                    // استدعاء ملف التوجيه اللي صممناه لتوليد الشاشات وتنقلها
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}