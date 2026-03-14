package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen

@Composable
fun DepartmentSettingsScreen(navController: NavController) {
    // Colors based on your UI
    val darkGreen = Color(0xFF1B3D2F)
    val lightGreyBg = Color(0xFFF8F9FA)
    val lightGreenIconBg = Color(0xFFEDF5F0) // Soft green for the icon background
    val beigeAccent = Color(0xFFFDF6E9) // Decorative circle color

    // State for the functional switches
    var receiveNotifications by remember { mutableStateOf(true) }
    var makeOfficeHoursPublic by remember { mutableStateOf(true) }

    BaseScreen(
        title = "Department Settings",
        subtitle = "Manage preferences",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = darkGreen)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGreyBg)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. PROFILE CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // Decorative beige circle at the top right
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawCircle(
                            color = beigeAccent,
                            radius = 120f,
                            center = Offset(size.width, 0f) // Top Right Corner
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Avatar
                        Surface(
                            shape = CircleShape,
                            color = darkGreen,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "DR",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Text Info
                        Column {
                            Text(
                                text = "Dr. Elena Rodriguez",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Senior Faculty • Computer Science",
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. SECTION HEADER
            Text(
                text = "NOTIFICATIONS & PRIVACY",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8DA09B), // Light grayish green
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            // 3. SETTINGS GROUP CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    // Row 1: Notifications
                    SwitchSettingRow(
                        icon = Icons.Default.Notifications,
                        title = "Receive Department\nNotifications",
                        iconBgColor = lightGreenIconBg,
                        iconColor = darkGreen,
                        isChecked = receiveNotifications,
                        onCheckedChange = { receiveNotifications = it }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFF0F0F0),
                        thickness = 1.dp
                    )

                    // Row 2: Public Office Hours
                    SwitchSettingRow(
                        icon = Icons.Default.Public,
                        title = "Make Office Hours Public",
                        iconBgColor = lightGreenIconBg,
                        iconColor = darkGreen,
                        isChecked = makeOfficeHoursPublic,
                        onCheckedChange = { makeOfficeHoursPublic = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// Reusable component for the rows with switches
@Composable
fun SwitchSettingRow(
    icon: ImageVector,
    title: String,
    iconBgColor: Color,
    iconColor: Color,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                lineHeight = 18.sp
            )
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF1B3D2F), // Dark Green
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}