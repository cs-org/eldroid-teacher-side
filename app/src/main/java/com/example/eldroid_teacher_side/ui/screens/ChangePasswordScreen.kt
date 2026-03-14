package com.example.eldroid_teacher_side.ui.screens

import android.widget.Toast // Added for the Toast simulation
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Added to get the context for the Toast
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.PasswordInputField
import com.example.eldroid_teacher_side.ui.components.RequirementRow

@Composable
fun ChangePasswordScreen(navController: NavController) {
    // Colors matching your design
    val darkGreen = Color(0xFF1B3D2F)
    val lightGreyBg = Color(0xFFF8F9FA)
    val goldAccent = Color(0xFFD4AF37)
    val textGrey = Color(0xFF7A8B85)

    // Form States
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Real-time Validation Logic
    val hasMinLength = newPassword.length >= 8
    val hasNumber = newPassword.any { it.isDigit() }
    val hasSpecialChar = newPassword.any { !it.isLetterOrDigit() }
    val isFormValid = hasMinLength && hasNumber && hasSpecialChar && (newPassword == confirmPassword) && newPassword.isNotEmpty()

    // Get the context for our Toast message
    val context = LocalContext.current

    BaseScreen(
        title = "Change Password",
        subtitle = "Account Security",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = darkGreen)
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(lightGreyBg)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (isFormValid) {
                            // Trigger the Toast Simulation!
                            Toast.makeText(context, "Password successfully updated!", Toast.LENGTH_SHORT).show()

                            // Navigate back to the previous screen
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = darkGreen,
                        disabledContainerColor = Color(0xFF8DA09B)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = isFormValid // Only clickable if all requirements are met!
                ) {
                    Text("UPDATE PASSWORD", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.Check, contentDescription = null, tint = goldAccent, modifier = Modifier.size(18.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Last changed: Just now", fontSize = 11.sp, color = Color.Gray) // I updated this text too!
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGreyBg)
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- HEADER BRANDING ---
            HorizontalDivider(color = goldAccent.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.width(180.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "COLEGIO DE ALICIA",
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = darkGreen,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Faculty Portal • Secure Access",
                fontSize = 12.sp,
                color = textGrey,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = goldAccent.copy(alpha = 0.5f), thickness = 1.dp, modifier = Modifier.width(180.dp))

            Spacer(modifier = Modifier.height(32.dp))

            // --- PASSWORD INPUT FIELDS ---
            Column(modifier = Modifier.fillMaxWidth()) {
                PasswordInputField(label = "Current Password", value = currentPassword) { currentPassword = it }
                Spacer(modifier = Modifier.height(16.dp))
                PasswordInputField(label = "New Password", value = newPassword) { newPassword = it }
                Spacer(modifier = Modifier.height(16.dp))
                PasswordInputField(label = "Confirm New Password", value = confirmPassword) { confirmPassword = it }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SECURITY REQUIREMENTS CARD ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "SECURITY REQUIREMENTS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFA0B0AA),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    RequirementRow("Minimum 8 characters", isMet = hasMinLength, darkGreen = darkGreen)
                    Spacer(modifier = Modifier.height(12.dp))
                    RequirementRow("At least one number (0-9)", isMet = hasNumber, darkGreen = darkGreen)
                    Spacer(modifier = Modifier.height(12.dp))
                    RequirementRow("At least one special character (@#$%^&*)", isMet = hasSpecialChar, darkGreen = darkGreen)
                }
            }

            Spacer(modifier = Modifier.height(120.dp)) // Padding for bottom bar
        }
    }
}

