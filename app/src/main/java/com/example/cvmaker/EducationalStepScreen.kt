package com.example.cvmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cvmaker.ui.theme.GreenPrimary
import com.example.cvmaker.ui.theme.BackgroundGray
import com.example.cvmaker.ui.theme.TextDark
import com.example.cvmaker.ui.theme.CardWhite

@Composable
fun EducationalStepScreen(navController: NavController) {
    // حقول الإدخال (State)
    var university by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var gradYear by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    // حالات الأخطاء والتحقق ديناميكياً (Validation State)
    var isUniError by remember { mutableStateOf(false) }
    var isSpecialtyError by remember { mutableStateOf(false) }
    var isYearError by remember { mutableStateOf(false) }

    // ألوان الحقول لضمان الخطوط الغامقة الواضحة والمقروءة 100%
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

        // مؤشر علوي فخم لرقم الخطوة الحالية (BPMN Style)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.step2_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // كرت محتوى الفورم الرئيسي للتعليم والخبرة
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
                    text = stringResource(id = R.string.step2_subtitle),
                    color = TextDark.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // 1. الجامعة
                OutlinedTextField(
                    value = university,
                    onValueChange = {
                        university = it
                        if (it.trim().isNotEmpty()) isUniError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_university)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isUniError,
                    supportingText = {
                        if (isUniError) {
                            Text(text = stringResource(id = R.string.error_empty_university), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 2. التخصص
                OutlinedTextField(
                    value = specialty,
                    onValueChange = {
                        specialty = it
                        if (it.trim().isNotEmpty()) isSpecialtyError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_specialty)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isSpecialtyError,
                    supportingText = {
                        if (isSpecialtyError) {
                            Text(text = stringResource(id = R.string.error_empty_specialty), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 3. سنة التخرج (مع تحقق صارم من 4 خانات)
                OutlinedTextField(
                    value = gradYear,
                    onValueChange = {
                        gradYear = it
                        if (it.trim().length == 4) isYearError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_graduation_year)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isYearError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = {
                        if (isYearError) {
                            Text(text = stringResource(id = R.string.error_invalid_year), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 4. الخبرات (اختياري متعدد الأسطر)
                OutlinedTextField(
                    value = experience,
                    onValueChange = { experience = it },
                    label = { Text(stringResource(id = R.string.hint_experience)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    minLines = 3
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // أزرار التحكم (السابق ,التالي)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // زر العودة برجعنا خطوة لورا (ع step1 هون)
            Button(
                onClick = { navController.navigate("step1") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_back), color = CardWhite)
            }

            // التالي مع Validation للخطوة التانية
            Button(
                onClick = {
                    val isUniEmpty = university.trim().isEmpty()
                    val isSpecialtyEmpty = specialty.trim().isEmpty()
                    val isYearInvalid = gradYear.trim().length != 4
                    if (isUniEmpty) isUniError = true
                    if (isSpecialtyEmpty) isSpecialtyError = true
                    if (isYearInvalid) isYearError = true

                    if (!isUniEmpty && !isSpecialtyEmpty && !isYearInvalid) {
                        navController.navigate("step3")
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