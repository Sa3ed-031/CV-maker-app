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
import androidx.compose.ui.platform.LocalContext
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
fun CvPreviewScreen(navController: NavController, viewModel: CvViewModel) {
    val context = LocalContext.current

    // حالة الـ Toggle للتبديل بين الاستايلات
    var isTwoColumn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // كرت العنوان العلوي
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite)
        ) {
            Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(id = R.string.preview_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // كرت الـ Switch للتبديل بين النمطين (الحديث والكلاسيكي)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isTwoColumn) "النمط الحديث (عمودين)" else "النمط الكلاسيكي (طولي)",
                    color = TextDark,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Switch(
                    checked = isTwoColumn,
                    onCheckedChange = { isTwoColumn = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = CardWhite,
                        checkedTrackColor = GreenPrimary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // عرض المعاينة الحية بناءً على خيار الـ Toggle والبيانات الحقيقية
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            if (isTwoColumn) {
                // عرض الـ UI بنظام العمودين المودرن
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(0.35f).fillMaxHeight()
                            .background(BackgroundGray)
                            .padding(12.dp)
                    ) {
                        Text(text = viewModel.fullName, color = TextDark, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "الهاتف: ${viewModel.phone}", color = TextDark.copy(alpha = 0.7f), fontSize = 11.sp)
                        Text(text = "الوطني: ${viewModel.nationalId}", color = TextDark.copy(alpha = 0.5f), fontSize = 10.sp)
                    }
                    Column(
                        modifier = Modifier
                            .weight(0.65f)
                            .fillMaxHeight()
                            .padding(12.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(text = "التعليم", color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = "${viewModel.university} - ${viewModel.specialty} (${viewModel.gradYear})", color = TextDark, fontSize = 11.sp)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "المشاريع والإنجازات", color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = viewModel.projects, color = TextDark, fontSize = 11.sp)
                    }
                }
            }
            else {
                // عرض الـ UI بنظام الكلاسيكي الطولي التقليدي
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(text = viewModel.fullName, color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = "الهاتف: ${viewModel.phone} | الرقم الوطني: ${viewModel.nationalId}", color = TextDark.copy(alpha = 0.7f), fontSize = 12.sp)

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Text(text = "المؤهلات الأكاديمية:", color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "${viewModel.university} (${viewModel.specialty}) - سنة: ${viewModel.gradYear}", color = TextDark, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "المشاريع والإنجازات:", color = GreenPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = viewModel.projects, color = TextDark, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 📥 أزرار التحكم السفلية وزر الـ Download
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("step4") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_back), color = CardWhite)
            }

            // استدعاء كلاس الطباعة الخارجي بسطر واحد فخم ومباشر
            Button(
                onClick = { PdfExporter.saveCv(context = context, isTwoColumn = isTwoColumn, viewModel = viewModel) },
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_final_export),
                    color = CardWhite,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}