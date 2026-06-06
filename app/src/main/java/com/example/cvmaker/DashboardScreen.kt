package com.example.cvmaker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

// كلاس وهمي لتمثيل الطلبات مؤقتاً في التصميم (Mock Data)
data class CvRequest(val id: String, val name: String, val nationalId: String)

@Composable
fun DashboardScreen(navController: NavController) {
    // حالة التبويب الحالي (0: بانتظار المراجعة, 1: المقبولة, 2: المرفوضة)
    var selectedTab by remember { mutableStateOf(0) }

    // بيانات وهمية (Mock Data) لتعبئة التصميم ويظهر بشكل حيوي أمام الآنسة
    val pendingRequests = listOf(
        CvRequest("1", "أحمد محمد العلي", "01020034451"),
        CvRequest("2", "فاطمة عمر القاسم", "04120098452")
    )
    val approvedRequests = listOf(
        CvRequest("3", "خالد وليد الحمصي", "01030044125")
    )
    val rejectedRequests = emptyList<CvRequest>() // قسم فارغ لتجربة حالة لا يوجد طلبات

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // هيدر اللوحة الرسمي
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
                    text = stringResource(id = R.string.admin_panel_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // التبويبات
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = CardWhite,
            contentColor = GreenPrimary,
            modifier = Modifier.background(BackgroundGray)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(stringResource(id = R.string.tab_pending), fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(stringResource(id = R.string.tab_approved), fontWeight = FontWeight.Bold) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = { Text(stringResource(id = R.string.tab_rejected), fontWeight = FontWeight.Bold) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // قائمة عرض الطلبات (LazyColumn) حسب التبويب
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            val currentList = when (selectedTab) {
                0 -> pendingRequests
                1 -> approvedRequests
                else -> rejectedRequests
            }
            if (currentList.isEmpty()) {
                // حالة عدم وجود طلبات
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.text_no_requests),
                        color = TextDark.copy(alpha = 0.5f),
                        fontSize = 16.sp
                    )
                }
            } else {
                // عرض الطلبات على شكل كروت
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(currentList) { request ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = CardWhite),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "${stringResource(id = R.string.label_applicant)} ${request.name}",
                                        color = TextDark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${stringResource(id = R.string.label_national_id_admin)} ${request.nationalId}",
                                        color = TextDark.copy(alpha = 0.6f),
                                        fontSize = 13.sp
                                    )
                                }

                                // زر الإجراء لمراجعة الطلب وفحص المستندات
                                Button(
                                    onClick = { /* كود فتح تفاصيل الطلب وقبوله أو رفضه لاحقاً */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.btn_action_view),
                                        color = CardWhite,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // logout
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("dashboard") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.btn_logout_admin),
                color = CardWhite,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}