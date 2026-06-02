package com.example.cvbuilder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cvbuilder.R
import com.example.cvbuilder.data.models.MockCVDatabase
import com.example.cvbuilder.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.dashboard_title)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.Editor.route) }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.create_new_cv))
            }
        }
    ) { paddingValues ->
        if (MockCVDatabase.savedCVs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.no_cvs), style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(MockCVDatabase.savedCVs) { cv ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set("cvData", cv)
                            navController.navigate(Screen.Preview.route)
                        }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = cv.fullName, style = MaterialTheme.typography.titleLarge)
                            Text(text = cv.jobTitle, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
}