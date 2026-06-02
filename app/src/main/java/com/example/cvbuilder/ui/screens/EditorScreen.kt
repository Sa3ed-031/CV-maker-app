package com.example.cvbuilder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cvbuilder.R
import com.example.cvbuilder.data.models.CVData
import com.example.cvbuilder.data.models.MockCVDatabase
import com.example.cvbuilder.navigation.Screen
import com.example.cvbuilder.ui.components.CustomButton
import com.example.cvbuilder.ui.components.CustomTextField
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(navController: NavHostController) {
    var currentStep by remember { mutableStateOf(0) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }

    var degree by remember { mutableStateOf("") }
    var institution by remember { mutableStateOf("") }

    var jobTitle by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }

    var skillsInput by remember { mutableStateOf("") }

    val titles = listOf(
        stringResource(R.string.personal_info),
        stringResource(R.string.education),
        stringResource(R.string.experience),
        stringResource(R.string.skills)
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.editor_title)) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            TabRow(selectedTabIndex = currentStep) {
                titles.forEachIndexed { index, title ->
                    Tab(selected = currentStep == index, onClick = { currentStep = index }, text = { Text(title) })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.weight(1f)) {
                when (currentStep) {
                    0 -> Column {
                        CustomTextField(value = fullName, onValueChange = { fullName = it }, label = stringResource(R.string.full_name))
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField(value = email, onValueChange = { email = it }, label = stringResource(R.string.email))
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField(value = phone, onValueChange = { phone = it }, label = stringResource(R.string.phone))
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField(value = summary, onValueChange = { summary = it }, label = stringResource(R.string.summary))
                    }
                    1 -> Column {
                        CustomTextField(value = degree, onValueChange = { degree = it }, label = stringResource(R.string.degree))
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField(value = institution, onValueChange = { institution = it }, label = stringResource(R.string.institution))
                    }
                    2 -> Column {
                        CustomTextField(value = jobTitle, onValueChange = { jobTitle = it }, label = stringResource(R.string.job_title))
                        Spacer(modifier = Modifier.height(16.dp))
                        CustomTextField(value = company, onValueChange = { company = it }, label = stringResource(R.string.company))
                    }
                    3 -> Column {
                        CustomTextField(value = skillsInput, onValueChange = { skillsInput = it }, label = stringResource(R.string.skills_comma))
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    text = stringResource(R.string.back),
                    onClick = { currentStep-- },
                    enabled = currentStep > 0,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomButton(
                    text = if (currentStep == 3) stringResource(R.string.preview) else stringResource(R.string.next),
                    onClick = {
                        if (currentStep < 3) {
                            currentStep++
                        } else {
                            val finalCV = CVData(
                                id = UUID.randomUUID().toString(),
                                fullName = fullName,
                                email = email,
                                phone = phone,
                                summary = summary,
                                degree = degree,
                                institution = institution,
                                jobTitle = jobTitle,
                                company = company,
                                skills = skillsInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                            )
                            MockCVDatabase.savedCVs.add(finalCV)
                            navController.currentBackStackEntry?.savedStateHandle?.set("cvData", finalCV)
                            navController.navigate(Screen.Preview.route)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}