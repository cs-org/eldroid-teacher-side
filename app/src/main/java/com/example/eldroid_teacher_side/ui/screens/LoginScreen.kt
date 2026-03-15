package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.ForgotPasswordButton
import com.example.eldroid_teacher_side.ui.components.LoginActionButton
import com.example.eldroid_teacher_side.ui.components.LoginFooter
import com.example.eldroid_teacher_side.ui.components.LoginForm
import com.example.eldroid_teacher_side.ui.components.LoginHeader
import com.example.eldroid_teacher_side.ui.components.QuickAccessSection

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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

            LoginActionButton(
                onClick = {
                    onLoginSuccess()
                }
            )

            QuickAccessSection(
                onBiometricClick = {
                    onLoginSuccess()
                }
            )

        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            LoginFooter()
        }
    }
}
