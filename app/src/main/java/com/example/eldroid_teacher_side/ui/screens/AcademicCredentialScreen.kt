package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.CredentialCard
import com.example.eldroid_teacher_side.viewmodels.CredentialsViewModel // Import your VM

@Composable
fun AcademicCredentialScreen(
    navController: NavController,
    viewModel: CredentialsViewModel = viewModel()
) {
    val credentials by viewModel.credentials.collectAsState()
    val mission by viewModel.mission.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    BaseScreen(
        title = "Academic Credentials",
        subtitle = "Official Faculty Records",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // HEADER SECTION
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Verified Qualifications", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("These records are verified by the University Registrar.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Box(
                    modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.School, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- LOADING OR CONTENT LOGIC ---
            if (isLoading) {
                // Show this while fetching from the Flask API
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp), // Height approximate to the list content
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else {
                // 1. DYNAMIC CREDENTIALS LIST
                if (credentials.isEmpty()) {
                    Text(
                        text = "No records found.",
                        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    credentials.forEach { cred ->
                        CredentialCard(
                            title = cred.degree_title,
                            institution = cred.institution,
                            year = cred.year_obtained,
                            isDegree = cred.type == "degree"
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 2. ACADEMIC PROFILE QUOTE CARD
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("ACADEMIC MISSION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary, letterSpacing = 1.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "\"$mission\"",
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            fontStyle = FontStyle.Italic,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "To update your academic records, please submit your original certificates to the HR Department.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}