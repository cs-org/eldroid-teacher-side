package com.example.eldroid_teacher_side.ui.screens

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

    // This block triggers ONLY when loginState changes to Success
    LaunchedEffect(loginState) {
        // If the state is Success, extract the data and pass it to MainActivity
        if (loginState is LoginState.Success) {
            val userData = (loginState as LoginState.Success).data
            onLoginSuccess(userData) // Pass the real data here!
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

            // Change this:
            ForgotPasswordButton(
                onForgotClick = {
                    navController.navigateSafe("request_otp") // Change from println to navigate
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
                        // Actually send the request to the database!
                        viewModel.login(email, password, tokenManager)
                    }
                )
            }

            QuickAccessSection(
                onBiometricClick = {
                    if (biometricHelper.canAuthenticate()) {
                        biometricHelper.showBiometricPrompt(
                            onSuccess = {
                                // For now, we just navigate as requested, but in a real app 
                                // we'd need to ensure we have a token or valid session.
                                navController.navigateSafe("main_content") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    } else {
                        // Optionally show a message that biometric is not available
                    }
                }
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            LoginFooter()
        }
    }
}