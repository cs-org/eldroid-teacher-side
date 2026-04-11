package com.example.eldroid_teacher_side.ui.components

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eldroid_teacher_side.viewmodels.ProfileViewModel
import coil.compose.AsyncImage

@Composable
fun ProfileDrawerContent(
    navController: NavController,
    onLogout: () -> Unit,
    onCloseDrawer: () -> Unit,
    viewModel: ProfileViewModel = viewModel() // Inject Profile VM
) {
    val context = LocalContext.current
    val user by viewModel.userProfile.collectAsState() // Observe user data

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. AVATAR SECTION
            item {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier.size(100.dp),
                        border = BorderStroke(3.dp, MaterialTheme.colorScheme.surface),
                        shadowElevation = 4.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        // UPDATED: Now uses AsyncImage to load the URL from your database
                        AsyncImage(
                            model = user.profileImage,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            // Fallback to the professor drawable while loading or on error
                            placeholder = painterResource(id = R.drawable.professor),
                            error = painterResource(id = R.drawable.professor)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // DYNAMIC NAME
                Text(
                    text = user.fullName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // DYNAMIC ROLE
                Text(
                    text = user.role,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                )

                // DYNAMIC FACULTY ID
                Text(
                    text = "Faculty ID: ${user.facultyId}",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = {
                            onCloseDrawer()
                            navController.navigate("personal_information")
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Update Photo", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, "Faculty Profile: ${user.fullName}")
                                val shareMessage = """
                                    👨‍🏫 ${user.fullName}
                                    ${user.role}
                                    Colegio de Alicia
                                    
                                    Email: ${user.email}
                                    ID: ${user.facultyId}
                                """.trimIndent()
                                putExtra(Intent.EXTRA_TEXT, shareMessage)
                            }
                            val chooser = Intent.createChooser(shareIntent, "Share Faculty Profile")
                            context.startActivity(chooser)
                        }
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.padding(10.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item { SectionHeader("GENERAL SETTINGS") }
            item {
                SettingsCard("Personal Info", "Email, Phone, Address", R.drawable.person, onClick = {
                    onCloseDrawer()
                    navController.navigate("personal_information")
                })
            }
            item {
                SettingsCard("Academic", "Degrees, Publications", R.drawable.grad_hat, onClick = {
                    onCloseDrawer()
                    navController.navigate("academic_credential")
                })
            }
            item {
                SettingsCard("Department", "Class assignments", R.drawable.account_balance, onClick = {
                    onCloseDrawer()
                    navController.navigate("department_settings")
                })
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item { SectionHeader("HELP & SECURITY") }
            item {
                SettingsCard("Security", "Password, 2FA", R.drawable.security, onClick = {
                    onCloseDrawer()
                    navController.navigate("security_privacy")
                })
            }
            item {
                SettingsCard("FAQs", "Questions", R.drawable.ic_launcher_foreground, onClick = {
                    onCloseDrawer()
                    navController.navigate("faq")
                })
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                SettingsCard(
                    title = "Log Out",
                    subtitle = null,
                    imageId = R.drawable.logout,
                    isDestructive = true,
                    onClick = {
                        onCloseDrawer()
                        onLogout()
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Version 2.4.0", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}