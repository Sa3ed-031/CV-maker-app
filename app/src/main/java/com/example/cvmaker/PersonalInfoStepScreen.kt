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
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage // تأكد أن مكتبة Coil مضافة في الـ build.gradle

@Composable
fun PersonalInfoStepScreen (
    navController: NavController,
    viewModel: CvViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.selectedImageUri = uri // حفظ مسار الصورة عند اختيارها
    }

    // وهي حالات الأخطاء والتحقق (Validation State)
    var isNameError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isIdError by remember { mutableStateOf(false) }

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
                    text = stringResource(id = R.string.step1_title),
                    color = GreenPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                    text = stringResource(id = R.string.step1_subtitle),
                    color = TextDark.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                OutlinedTextField(
                    value = viewModel.fullName,
                    onValueChange = {
                        viewModel.fullName = it
                        if (it.trim().isNotEmpty()) isNameError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_full_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isNameError,
                    supportingText = {
                        if (isNameError) {
                            Text(text = stringResource(id = R.string.error_empty_name), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.phone,
                    onValueChange = {
                        viewModel.phone = it
                        if (it.trim().length >= 10) isPhoneError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_phone_number)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isPhoneError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    supportingText = {
                        if (isPhoneError) {
                            Text(text = stringResource(id = R.string.error_invalid_phone), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.nationalId,
                    onValueChange = {
                        viewModel.nationalId = it
                        if (it.trim().isNotEmpty()) isIdError = false
                    },
                    label = { Text(stringResource(id = R.string.hint_national_id)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = fieldColors,
                    isError = isIdError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = {
                        if (isIdError) {
                            Text(text = stringResource(id = R.string.error_empty_id), color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // الصورة
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(2.dp, GreenPrimary, CircleShape)
                        .clickable {
                            // فتح الاستوديو حصراً لاختيار الصور عند النقر
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (viewModel.selectedImageUri != null) {
                        // هون استخدمنا مكتبة coil لعرض الصورة
                        AsyncImage(
                            model = viewModel.selectedImageUri,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // نص أو إيقونة تظهر في حال لم يتم اختيار صورة بعد
                        Text(
                            text = "إضافة\nصورة",
                            color = TextDark.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // (السابق, التالي)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("login") },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_back), color = CardWhite)
            }

            Button(
                onClick = {
                    val isNameEmpty = viewModel.fullName.trim().isEmpty()
                    val isPhoneInvalid = viewModel.phone.trim().length < 10
                    val isIdEmpty = viewModel.nationalId.trim().isEmpty()

                    if (isNameEmpty) isNameError = true
                    if (isPhoneInvalid) isPhoneError = true
                    if (isIdEmpty) isIdError = true

                    if (!isNameEmpty && !isPhoneInvalid && !isIdEmpty) {
                        navController.navigate("step2")
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