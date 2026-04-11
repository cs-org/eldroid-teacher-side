package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.*
import com.example.eldroid_teacher_side.viewmodels.LoginState
import com.example.eldroid_teacher_side.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: (com.example.eldroid_teacher_side.ui.data.FacultyData) -> Unit
) {
    var email by remember { mutableStateOf("") } // Used as Faculty ID
    var password by remember { mutableStateOf("") }

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

            ForgotPasswordButton(
                onForgotClick = {
                    println("Forgot Password Clicked!")
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
                        viewModel.login(email, password)
                    }
                )
            }

            QuickAccessSection(
                onBiometricClick = {
                    // For prototype purposes, biometric just forces a success
                    //onLoginSuccess()
                }
            )
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            LoginFooter()
        }
    }
}