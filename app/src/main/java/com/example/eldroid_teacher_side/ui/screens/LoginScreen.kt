package com.example.eldroid_teacher_side.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.network.ChatSocketHandler
import com.example.eldroid_teacher_side.network.TokenManager
import com.example.eldroid_teacher_side.ui.components.*
import com.example.eldroid_teacher_side.util.BiometricHelper
import com.example.eldroid_teacher_side.util.navigateSafe
import com.example.eldroid_teacher_side.viewmodels.LoginState
import com.example.eldroid_teacher_side.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    tokenManager: TokenManager,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (com.example.eldroid_teacher_side.ui.data.FacultyData) -> Unit
) {
    var email by remember { mutableStateOf("") } // Used as Faculty ID
    var password by remember { mutableStateOf("") }
    
    val context = LocalContext.current
    val biometricHelper = remember { BiometricHelper(context as FragmentActivity) }

    // Observe the login state from our ViewModel
    val loginState by viewModel.loginState.collectAsState()

    // This block triggers ONLY when loginState changes to Success (Manual Login)
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            val userData = (loginState as LoginState.Success).data
            onLoginSuccess(userData)
            viewModel.resetState()
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginHeader(
                headerText = "Colegio De Alicia",
                subText = "FACULTY PORTAL"
            )

            LoginForm(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it }
            )

            ForgotPasswordButton(
                onForgotClick = {
                    navController.navigateSafe("request_otp") 
                }
            )

            // --- ERROR MESSAGE UI ---
            if (loginState is LoginState.Error) {
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // --- LOADING / LOGIN BUTTON ---
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(
                    color = Color(0xFF004020),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LoginActionButton(
                    onClick = {
                        viewModel.login(email, password, tokenManager)
                    }
                )
            }

            QuickAccessSection(
                onBiometricClick = {
                    val token = tokenManager.getToken()
                    
                    // Production Recommendation: Better feedback and state checking
                    if (token == null) {
                        Toast.makeText(context, "Please sign in manually first", Toast.LENGTH_LONG).show()
                    } else if (biometricHelper.canAuthenticate()) {
                        biometricHelper.showBiometricPrompt(
                            onSuccess = {
                                // UX Recommendation: Friendlier message
                                Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                                
                                // Consolidate session initialization
                                performSessionInitialization(context, token)

                                navController.navigateSafe("main_content") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            LoginFooter()
        }
    }
}

/**
 * Consolidates background tasks needed to start a session.
 */
private fun performSessionInitialization(context: Context, token: String) {
    // 1. Mark user as logged in for the next app launch
    val sharedPrefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    sharedPrefs.edit().putBoolean("is_logged_in", true).apply()

    // 2. Initialize real-time services
    ChatSocketHandler.init(token)
    ChatSocketHandler.connect()
}
