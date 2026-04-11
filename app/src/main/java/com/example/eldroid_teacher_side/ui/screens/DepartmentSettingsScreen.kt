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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.SwitchSettingRow
import com.example.eldroid_teacher_side.viewmodels.ProfileViewModel

@Composable
fun DepartmentSettingsScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel() // Inject ViewModel
) {
    // Collect the dynamic user profile state
    val user by viewModel.userProfile.collectAsState()

    // Get the first letter of the name for the avatar placeholder
    val initial = remember(user.fullName) {
        user.fullName.takeIf { it.isNotEmpty() }?.take(1)?.uppercase() ?: "?"
    }

    // State for the functional switches
    var receiveNotifications by remember { mutableStateOf(true) }
    var makeOfficeHoursPublic by remember { mutableStateOf(true) }

    BaseScreen(
        title = "Department Settings",
        subtitle = "Manage preferences",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. DYNAMIC PROFILE CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    val tertiaryColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                    Canvas(modifier = Modifier.matchParentSize()) {
                        drawCircle(
                            color = tertiaryColor,
                            radius = 120f,
                            center = Offset(size.width, 0f)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Dynamic Initial Avatar
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = initial, // DYNAMIC INITIAL
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // DYNAMIC TEXT INFO
                        Column {
                            Text(
                                text = user.fullName, // DYNAMIC NAME
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${user.role} • Department of Arts", // DYNAMIC ROLE
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
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
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            // 3. SETTINGS GROUP CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    SwitchSettingRow(
                        icon = Icons.Default.Notifications,
                        title = "Receive Department\nNotifications",
                        iconBgColor = MaterialTheme.colorScheme.primaryContainer,
                        iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        isChecked = receiveNotifications,
                        onCheckedChange = { receiveNotifications = it }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        thickness = 1.dp
                    )

                    SwitchSettingRow(
                        icon = Icons.Default.Public,
                        title = "Make Office Hours Public",
                        iconBgColor = MaterialTheme.colorScheme.primaryContainer,
                        iconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        isChecked = makeOfficeHoursPublic,
                        onCheckedChange = { makeOfficeHoursPublic = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}