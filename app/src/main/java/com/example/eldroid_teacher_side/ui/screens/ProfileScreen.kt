package com.example.eldroid_teacher_side.ui.screens

import android.content.Intent // Added for Share Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // Added for Context
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.SectionHeader
import com.example.eldroid_teacher_side.ui.components.SettingsCard
import com.example.eldroid_teacher_side.ui.components.BaseScreen

@Composable
fun ProfileScreen(
    navController: NavController,
    onLogout: () -> Unit
){
    // Context needed to launch the share sheet
    val context = LocalContext.current

    // State to hold the selected image URI
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher for the Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUri = uri
            }
        }
    )

    BaseScreen(
        title = "Faculty Profile",
        subtitle = "Teacher Settings",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        },

        ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // Use theme background
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
                        border = BorderStroke(4.dp, MaterialTheme.colorScheme.surface),
                        shadowElevation = 4.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        // Conditionally render the selected image or the default drawable
                        if (selectedImageUri != null) {
                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.professor), // Replace with your image
                                contentDescription = "Profile",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(32.dp)
                            .offset(x = (-4).dp, y = (-4).dp)
                            .clickable {
                                // Launch picker when edit button is clicked
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(6.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Prof. Reyes", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Text("Senior Lecturer, Department of Arts", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                Text("Faculty ID: 2023-00154", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { navController.navigate("personal_information")},
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Edit Profile", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Faculty Profile: Prof. Reyes")

                                // Using a multi-line string for a realistic "Digital Business Card" format
                                val shareMessage = """
                                    👨‍🏫 Prof. Reyes
                                    Senior Lecturer, Department of Arts
                                    Colegio de Alicia
                                    
                                    View my full academic profile, credentials, and book office hours here:
                                    🔗 https://portal.colegiodealicia.edu/faculty/reyes
                                """.trimIndent()

                                putExtra(Intent.EXTRA_TEXT, shareMessage)
                            }

                            // Create a chooser to make it look like a real app share sheet
                            val chooser = Intent.createChooser(shareIntent, "Share Faculty Profile")
                            context.startActivity(chooser)
                        }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.padding(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item { SectionHeader("GENERAL SETTINGS") }
            item {
                SettingsCard("Personal Information", "Email, Phone, and Address", R.drawable.person, onClick = { navController.navigate("personal_information") })
            }
            item {
                SettingsCard("Academic Credentials", "Degrees, Certifications, Publications", R.drawable.grad_hat, onClick = { navController.navigate("academic_credential")})
            }
            item {
                SettingsCard("Department Settings", "Manage class assignments and office hours", R.drawable.account_balance, onClick = { navController.navigate("department_settings")})
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SectionHeader("HELP & SECURITY") }
            item {
                SettingsCard("Security & Privacy", "Password, 2FA, Session management", R.drawable.security, onClick = { navController.navigate("security_privacy")})
            }
            item {
                // Moved FAQ to Profile/Settings screen
                SettingsCard("FAQs", "Frequently Asked Questions", R.drawable.ic_launcher_foreground, onClick = { navController.navigate("faq")})
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                SettingsCard(
                    title = "Log Out",
                    subtitle = null,
                    imageId = R.drawable.logout,
                    isDestructive = true,
                    onClick = {
                        onLogout()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("App Version 2.4.0", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("© Colegio de Alicia Institutional Portal", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
