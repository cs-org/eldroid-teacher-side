package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.SectionHeader
import com.example.eldroid_teacher_side.ui.components.SettingsCard
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.BottomBar

@Composable
fun ProfileScreen(navController: NavController){
    BaseScreen(
        title = "Faculty Profile",
        subtitle = "Teacher Settings",
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = Color(0xFF004020),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* More options */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color(0xFF1B3D2F))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FA)) // Light grey background like image
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. AVATAR SECTION
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(120.dp),
                        border = BorderStroke(4.dp, Color.White),
                        shadowElevation = 4.dp
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.professor), // Replace with your image
                            contentDescription = "Profile",
                            contentScale = ContentScale.Crop
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF1B3D2F),
                        modifier = Modifier.size(32.dp).offset(x = (-4).dp, y = (-4).dp),
                        border = BorderStroke(2.dp, Color.White)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.padding(6.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Prof. Reyes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B3D2F))
                Text("Senior Lecturer, Department of Arts", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                Text("Faculty ID: 2023-00154", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { /* Edit */ },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B3D2F)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Edit Profile", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE0E0E0),
                        onClick = { /* Share */ }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.padding(12.dp), tint = Color.DarkGray)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item { SectionHeader("GENERAL SETTINGS") }
            item {
                SettingsCard("Personal Information", "Email, Phone, and Address", R.drawable.person)
            }
            item {
                SettingsCard("Academic Credentials", "Degrees, Certifications, Publications", R.drawable.grad_hat)
            }
            item {
                SettingsCard("Department Settings", "Manage class assignments and office hours", R.drawable.account_balance)
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SectionHeader("SECURITY") }
            item {
                SettingsCard("Security & Privacy", "Password, 2FA, Session management", R.drawable.security)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                SettingsCard(
                    title = "Log Out",
                    subtitle = null,
                    imageId = R.drawable.logout,
                    isDestructive = true,
                    onClick = {
                        navController.navigate("login") {
                            popUpTo(navController.graph.startDestinationId){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("App Version 2.4.0", fontSize = 12.sp, color = Color.Gray)
                Text("© Colegio de Alicia Institutional Portal", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}