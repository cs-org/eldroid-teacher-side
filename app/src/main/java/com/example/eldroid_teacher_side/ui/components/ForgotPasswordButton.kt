package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ForgotPasswordButton(
    onForgotClick: () -> Unit // This name must match what you use below
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        TextButton(
            onClick = onForgotClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = "Forgot Password?",
                color = Color(0xFF1B3D2F),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}