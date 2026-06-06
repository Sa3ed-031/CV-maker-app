package com.example.cvmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cvmaker.ui.theme.GreenPrimary
import com.example.cvmaker.ui.theme.BackgroundGray
import com.example.cvmaker.ui.theme.TextDark
import com.example.cvmaker.ui.theme.CardWhite

@Composable
fun SkillsStepScreen(
    navController: NavController,
    viewModel: CvViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // حقول الإدخال (State)
    var skills by remember { mutableStateOf("") }
    var languages by remember { mutableStateOf("") }

    // (Validation State)
    var isSkillsError by remember { mutableStateOf(false) }
    var isLanguagesError by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = TextDark,
        unfocusedTextColor = TextDark,
        focusedBorderColor = GreenPrimary,
        unfocusedBorderColor = TextDark.copy(alpha = 0.3f),
        focusedLabelColor = GreenPrimary,
        unfocusedLabelColor = TextDark.copy(alpha = 0.6f),
        errorBorderColor = MaterialTheme.colorScheme.error,
        errorLabelColor = MaterialTheme.colorScheme.error
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // مؤشر علوي لرقم الخطوة الحالية
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.step3_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // كرت محتوى الفورم الرئيسي للمهارات واللغات
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp).fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.step3_subtitle),
                    color = TextDark.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // 1. المهارات (حقل كبير وكذا الأسطر إجباري)
                OutlinedTextField(
                    value = viewModel.skills,
                    onValueChange = {
                        viewModel.skills = it
                        if (it.trim().isNotEmpty()) isSkillsError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_skills)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isSkillsError,
                    minLines = 4,
                    supportingText = {
                        if (isSkillsError) {
                            Text(text = stringResource(id = R.string.error_empty_skills), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 2. اللغات (إجباري)
                OutlinedTextField(
                    value = viewModel.languages,
                    onValueChange = {
                        viewModel.languages = it
                        if (it.trim().isNotEmpty()) isLanguagesError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_languages)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isLanguagesError,
                    minLines = 2,
                    supportingText = {
                        if (isLanguagesError) {
                            Text(text = stringResource(id = R.string.error_empty_languages), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // أزرار التحكم (السابق ,التالي)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // زر العودة ( هون ع step2)
            Button(
                onClick = { navController.navigate("step2") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_back), color = CardWhite)
            }

            // التالي مع الـ Validation الصارم للخطوة الثالثة
            Button(
                onClick = {
                    val isSkillsEmpty = viewModel.skills.trim().isEmpty()
                    val isLanguagesEmpty = viewModel.languages.trim().isEmpty()

                    if (isSkillsEmpty) isSkillsError = true
                    if (isLanguagesEmpty) isLanguagesError = true

                    if (!isSkillsEmpty && !isLanguagesEmpty) {
                        navController.navigate("step4")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_next), color = CardWhite, fontWeight = FontWeight.Bold)
            }
        }
    }
}