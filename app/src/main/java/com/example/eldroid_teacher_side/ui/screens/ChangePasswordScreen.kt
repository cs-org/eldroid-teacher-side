package com.example.eldroid_teacher_side.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.PasswordInputField
import com.example.eldroid_teacher_side.ui.components.RequirementRow

@Composable
fun ChangePasswordScreen(navController: NavController) {
    // Using MaterialTheme.colorScheme for consistency across the app
    val colorScheme = MaterialTheme.colorScheme

    // Form States
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Real-time Validation Logic
    val hasMinLength = newPassword.length >= 8
    val hasNumber = newPassword.any { it.isDigit() }
    val hasSpecialChar = newPassword.any { !it.isLetterOrDigit() }
    val isFormValid = hasMinLength && hasNumber && hasSpecialChar && (newPassword == confirmPassword) && newPassword.isNotEmpty()

    val context = LocalContext.current

    BaseScreen(
        title = "Change Password",
        subtitle = "Account Security",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorScheme.primary
                )
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (isFormValid) {
                            Toast.makeText(context, "Password successfully updated!", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        disabledContainerColor = colorScheme.onSurface.copy(alpha = 0.12f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = isFormValid
                ) {
                    Text(
                        "UPDATE PASSWORD",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = colorScheme.tertiary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "Last changed: Just now",
                    fontSize = 11.sp,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- HEADER BRANDING ---
            HorizontalDivider(
                color = colorScheme.tertiary.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.width(180.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "COLEGIO DE ALICIA",
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colorScheme.primary,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Faculty Portal • Secure Access",
                fontSize = 12.sp,
                color = colorScheme.onSurfaceVariant,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                color = colorScheme.tertiary.copy(alpha = 0.5f),
                thickness = 1.dp,
                modifier = Modifier.width(180.dp)
            )

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
                colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "SECURITY REQUIREMENTS",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    RequirementRow("Minimum 8 characters", isMet = hasMinLength)
                    Spacer(modifier = Modifier.height(12.dp))
                    RequirementRow("At least one number (0-9)", isMet = hasNumber)
                    Spacer(modifier = Modifier.height(12.dp))
                    RequirementRow("At least one special character (@#$%^&*)", isMet = hasSpecialChar)
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}
