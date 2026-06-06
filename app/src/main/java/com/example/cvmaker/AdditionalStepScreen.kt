package com.example.cvmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cvmaker.ui.theme.GreenPrimary
import com.example.cvmaker.ui.theme.BackgroundGray
import com.example.cvmaker.ui.theme.TextDark
import com.example.cvmaker.ui.theme.CardWhite

@Composable
fun AdditionalStepScreen(navController: NavController) {
    val context =androidx.compose.ui.platform.LocalContext
    val scrollState = rememberScrollState()
    val dynamicPlaceholder = stringResource(id = R.string.placeholder_dynamic_data)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // مؤشر علوي لرقم الخطوة
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
                    text = stringResource(id = R.string.step4_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // كرت مراجعة المحتوى (Scrollable Card)
        Card(
            modifier = Modifier.fillMaxWidth().weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = stringResource(id = R.string.step4_subtitle),
                    color = TextDark.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                // بيانات شخصية
                Text(text = stringResource(id = R.string.section_personal_title), color = GreenPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = TextDark.copy(alpha = 0.1f))
                Text(text = "${stringResource(id = R.string.label_full_name)} $dynamicPlaceholder", color = TextDark, fontSize = 14.sp)
                Text(text = "${stringResource(id = R.string.label_phone_number)} $dynamicPlaceholder", color = TextDark, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(20.dp))

                // المؤهلات
                Text(text = stringResource(id = R.string.section_academic_title), color = GreenPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = TextDark.copy(alpha = 0.1f))
                Text(text = "${stringResource(id = R.string.label_university)} $dynamicPlaceholder", color = TextDark, fontSize = 14.sp)
                Text(text = "${stringResource(id = R.string.label_specialty)} $dynamicPlaceholder", color = TextDark, fontSize = 14.sp)

                Spacer(modifier = Modifier.height(20.dp))

                // ️المهارات واللغات
                Text(text = stringResource(id = R.string.section_skills_title), color = GreenPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = TextDark.copy(alpha = 0.1f))
                Text(text = "${stringResource(id = R.string.label_skills)} $dynamicPlaceholder", color = TextDark, fontSize = 14.sp)
                Text(text = "${stringResource(id = R.string.label_languages)} $dynamicPlaceholder", color = TextDark, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // زرار التحكم
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("step3") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_back), color = CardWhite)
            }

            val context = androidx.compose.ui.platform.LocalContext.current

            Button(
                onClick = {
                    PdfExporter.exportCvToPdf(
                        context = context,
                        fullName = "أحمد محمد العلي",
                        phone = "0933123456",
                        nationalId = "01020034451",
                        university = "جامعة دمشق",
                        specialty = "هندسة البرمجيات",
                        gradYear = "2026",
                        experience = "مطور واجهات أمامية مستقل\nبناء أنظمة تسجيل حكومية متكاملة بـ Jetpack Compose",
                        skills = "Kotlin, Jetpack Compose, UI Design, Git",
                        languages = "العربية (اللغة الأم)، الإنجليزية (متقدم)"
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.btn_export_cv),
                    color = CardWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}