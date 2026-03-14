package com.example.eldroid_teacher_side.ui.screens

import android.net.Uri
import android.widget.Toast // Added for the Toast simulation
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // Added to get the context
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.BaseScreen

@Composable
fun PersonalInformationScreen(navController: NavController) {
    // Colors based on your UI
    val darkGreen = Color(0xFF1B3D2F)
    val lightGreyBg = Color(0xFFF8F9FA)
    val goldAccent = Color(0xFFD4AF37)
    val iconBgColor = Color(0xFFE8EBE9)

    // Get context for the Toast
    val context = LocalContext.current

    // Image Picker State
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> if (uri != null) selectedImageUri = uri }
    )

    // Form Editable States
    var fullName by remember { mutableStateOf("Prof. Reyes") }
    var email by remember { mutableStateOf("reyes.prof@university.edu") }
    var phone by remember { mutableStateOf("09171234567") } // Formatted to match 11-digit rule
    var address by remember { mutableStateOf("123 Acacia Avenue, Quezon City, Metro Manila, 1101") }

    BaseScreen(
        title = "Personal Information",
        subtitle = "Manage your details",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = darkGreen)
            }
        },
        actions = {
            IconButton(onClick = {
                // Trigger Toast from top bar checkmark
                Toast.makeText(context, "Personal information saved successfully!", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save", tint = darkGreen)
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        // Trigger Toast from bottom button
                        Toast.makeText(context, "Personal information saved successfully!", Toast.LENGTH_SHORT).show()
                        println("Saving Data: $fullName, $email, $phone, $address")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_save),
                        contentDescription = "Save",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save Changes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. PROFILE AVATAR CARD
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Surface(
                            shape = CircleShape,
                            modifier = Modifier.size(100.dp),
                            border = BorderStroke(3.dp, goldAccent)
                        ) {
                            if (selectedImageUri != null) {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Profile Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.professor),
                                    contentDescription = "Profile",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        // Camera Edit Icon
                        Surface(
                            shape = CircleShape,
                            color = darkGreen,
                            modifier = Modifier
                                .size(28.dp)
                                .offset(x = (-4).dp, y = (-4).dp)
                                .clickable {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                            border = BorderStroke(2.dp, Color.White)
                        ) {
                            Icon(
                                Icons.Outlined.CameraAlt,
                                contentDescription = "Edit Picture",
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(fullName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = darkGreen)
                    Text("Senior Faculty Member", fontSize = 13.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 2. FULL NAME CARD (Editable)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Full Name", fontSize = 11.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(4.dp))
                    BasicTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        textStyle = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Medium, color = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. FACULTY ID (Read-Only styling)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F4F3)), // Slightly darker grey
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Faculty ID", fontSize = 11.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("2023-00154", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = darkGreen)
                    }
                    Icon(Icons.Default.Lock, contentDescription = "Locked", tint = goldAccent, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. CONTACT DETAILS CARD (Editable)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("CONTACT DETAILS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = goldAccent)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Email Row
                    EditableInfoRow(
                        icon = Icons.Default.Email,
                        label = "Email Address",
                        value = email,
                        onValueChange = { email = it },
                        iconBgColor = iconBgColor,
                        iconColor = darkGreen,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Phone Row - WITH NUMBER VALIDATION
                    EditableInfoRow(
                        icon = Icons.Default.Phone,
                        label = "Phone Number",
                        value = phone,
                        onValueChange = { newValue ->
                            // Rule: Must be exactly digits only, and max length of 11
                            if (newValue.all { it.isDigit() } && newValue.length <= 11) {
                                phone = newValue
                            }
                        },
                        iconBgColor = iconBgColor,
                        iconColor = darkGreen,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Opens number pad
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 5. HOME ADDRESS CARD (Editable)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    EditableInfoRow(
                        icon = Icons.Default.Home,
                        label = "Home Address",
                        value = address,
                        onValueChange = { address = it },
                        iconBgColor = iconBgColor,
                        iconColor = darkGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp)) // Extra padding before bottom bar
        }
    }
}

// Reusable component for the editable rows with icons
@Composable
fun EditableInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    iconBgColor: Color,
    iconColor: Color,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 11.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black),
                keyboardOptions = keyboardOptions, // Passes the keyboard type down to the text field
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}