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
fun PersonalInfoStepScreen(navController: NavController) {
    // هي حقول المدخلات (State)
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var nationalId by remember { mutableStateOf("") }

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
                    value = fullName,
                    onValueChange = {
                        fullName = it
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
                    value = phoneNumber,
                    onValueChange = {
                        phoneNumber = it
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
                    value = nationalId,
                    onValueChange = {
                        nationalId = it
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
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // زرار التحكم (السابق, التالي)
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
                    val isNameEmpty = fullName.trim().isEmpty()
                    val isPhoneInvalid = phoneNumber.trim().length < 10
                    val isIdEmpty = nationalId.trim().isEmpty()

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