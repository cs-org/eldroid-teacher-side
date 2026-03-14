package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DesktopMac
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import kotlinx.coroutines.launch

// Simple Data Class for the simulation
data class Device(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val isActive: Boolean
)

@Composable
fun SecurityPrivacyScreen(navController: NavController) {
    // Colors matching your design
    val darkGreen = Color(0xFF1B3D2F)
    val lightGreyBg = Color(0xFFF8F9FA)
    val sectionHeaderColor = Color(0xFF2E5A44)
    val redAccent = Color(0xFFD32F2F)

    // Custom Toggle Colors
    val toggleTrackYellow = Color(0xFFEAB345)
    val toggleThumbBlue = Color(0xFF2180D8)

    // States for functional switches
    var tfaEnabled by remember { mutableStateOf(true) }
    var biometricEnabled by remember { mutableStateOf(true) }

    // For Snackbar popups
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Dynamic List of Devices
    var devices by remember {
        mutableStateOf(
            listOf(
                Device("iPhone 14 Pro", "Quezon City, Philippines", Icons.Outlined.Smartphone, true),
                Device("MacBook Air M2", "Manila, Philippines • 2 hours ago", Icons.Outlined.DesktopMac, false)
            )
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }, // Enables snackbars
        topBar = {
            // Rebuilding just the top bar part since we are using Scaffold for the snackbar
            Surface(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = darkGreen)
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text("Security & Privacy", color = darkGreen, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text("Manage your account security", color = Color(0xFF5A6B81), fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightGreyBg)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                OutlinedButton(
                    onClick = {
                        // THE SIMULATION: Remove all inactive devices!
                        devices = devices.filter { it.isActive }

                        // Show a success message!
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Signed out of all other devices successfully.")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFFFCDD2)),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = redAccent)
                ) {
                    Text("Sign out from all devices", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
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

            // --- AUTHENTICATION SECTION ---
            Text(
                text = "AUTHENTICATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = sectionHeaderColor,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    // Change Password
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("change_password") }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Lock, contentDescription = null, tint = darkGreen, modifier = Modifier.size(22.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Change Password", fontSize = 14.sp, color = Color.Black)
                        }
                        Icon(Icons.Outlined.ChevronRight, contentDescription = null, tint = Color.LightGray)
                    }

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0), thickness = 1.dp)

                    // Two-Factor Authentication
                    SecurityToggleRow(
                        icon = Icons.Outlined.Security,
                        title = "Two-Factor Authentication",
                        iconColor = darkGreen,
                        isChecked = tfaEnabled,
                        onCheckedChange = { tfaEnabled = it },
                        trackColor = toggleTrackYellow,
                        thumbColor = toggleThumbBlue
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0), thickness = 1.dp)

                    // Biometric Login
                    SecurityToggleRow(
                        icon = Icons.Outlined.Fingerprint,
                        title = "Biometric Login",
                        iconColor = darkGreen,
                        isChecked = biometricEnabled,
                        onCheckedChange = { biometricEnabled = it },
                        trackColor = toggleTrackYellow,
                        thumbColor = toggleThumbBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- LOGIN ACTIVITY SECTION ---
            Text(
                text = "LOGIN ACTIVITY",
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = sectionHeaderColor,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column {
                    // Dynamic Device List Loop
                    devices.forEachIndexed { index, device ->
                        DeviceRow(
                            icon = device.icon,
                            iconBgColor = if (device.isActive) Color(0xFFEDF7F1) else Color(0xFFF5F6F8),
                            iconColor = if (device.isActive) darkGreen else Color.Gray,
                            title = device.title,
                            subtitle = device.subtitle,
                            statusText = if (device.isActive) "ACTIVE NOW" else null,
                            statusColor = if (device.isActive) Color(0xFF2E7D32) else Color.Transparent
                        )

                        // Add divider unless it's the very last item in the whole card
                        if (index < devices.size - 1 || devices.size <= 2) {
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF0F0F0), thickness = 1.dp)
                        }
                    }

                    // View all history button (Only show if we haven't expanded the list yet)
                    if (devices.size <= 2) {
                        TextButton(
                            onClick = {
                                // THE SIMULATION: Load more older devices!
                                devices = devices + listOf(
                                    Device("iPad Pro", "Cebu City, Philippines • 5 days ago", Icons.Outlined.Smartphone, false),
                                    Device("Windows PC", "Makati, Philippines • 2 weeks ago", Icons.Outlined.DesktopMac, false)
                                )
                            },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text("View all login history", color = sectionHeaderColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Extra padding so scroll clears the bottom bar
        }
    }
}

// Reusable component for the custom colored toggles
@Composable
fun SecurityToggleRow(
    icon: ImageVector,
    title: String,
    iconColor: Color,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    trackColor: Color,
    thumbColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontSize = 14.sp, color = Color.Black)
        }

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = thumbColor,
                checkedTrackColor = trackColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray,
                uncheckedBorderColor = Color.Transparent
            ),
            thumbContent = if (isChecked) {
                { Icon(imageVector = Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(SwitchDefaults.IconSize), tint = Color.White) }
            } else { null }
        )
    }
}

// Reusable component for the device list
@Composable
fun DeviceRow(
    icon: ImageVector,
    iconBgColor: Color,
    iconColor: Color,
    title: String,
    subtitle: String,
    statusText: String?,
    statusColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }

        if (statusText != null) {
            Text(text = statusText, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = statusColor, letterSpacing = 0.5.sp)
        }
    }
}