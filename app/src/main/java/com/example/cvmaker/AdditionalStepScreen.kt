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
fun AdditionalStepScreen(
    navController: NavController,
    viewModel: CvViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var isProjectsError by remember { mutableStateOf(false) }
    var isExperienceError by remember { mutableStateOf(false) }

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

        // كرت العنوان العلوي للخطوة الرابعة
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.step4_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // كرت فورم الإدخال
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.step4_subtitle),
                    color = TextDark.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // 1. حقل إدخال المشاريع متعدد الأسطر
                OutlinedTextField(
                    value = viewModel.projects,
                    onValueChange = {
                        viewModel.projects = it
                        if (it.trim().isNotEmpty()) isProjectsError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_projects)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isProjectsError,
                    minLines = 5,
                    supportingText = {
                        if (isProjectsError) {
                            Text(
                                text = stringResource(id = R.string.error_empty_projects),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 2. الخبرات (اختياري متعدد الأسطر
                OutlinedTextField(
                    value = viewModel.experience,
                    onValueChange = {
                        viewModel.experience = it
                        if (it.trim().isNotEmpty()) isExperienceError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_experience)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isExperienceError,
                    minLines = 3,
                    supportingText = {
                        if (isExperienceError) {
                            Text(
                                text = stringResource(id = R.string.error_empty_projects),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // أزرار التحكم والتنقل السفلية
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // زر رجوع للخطوة الثالثة (المهارات)
            Button(
                onClick = { navController.navigate("step3") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_back), color = CardWhite)
            }

            // زر التالي بينتقل لشاشة الـ Preview بعد التحقق من الإدخال
            Button(
                onClick = {

                    val isProjectsEmpty = viewModel.projects.trim().isEmpty()
                    val isExperienceEmpty = viewModel.experience.trim().isEmpty()

                    if (isProjectsEmpty) isProjectsError= true
                    if (isExperienceEmpty) isExperienceError = true

                    if (!isProjectsEmpty && !isExperienceEmpty) {
                        navController.navigate("cv_preview")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_next),
                    color = CardWhite,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}