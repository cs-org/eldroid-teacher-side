package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@Composable
fun LoginHeader(headerText: String, subText: String) {
    val image = painterResource(R.drawable.logo_2)
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(80.dp))

        Image(
            painter = image,
            contentDescription = "Colegio de Alicai Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(8.dp)
        )

        Text(
            text = headerText,
            modifier = Modifier,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004020)
        )

        Text(
            text = subText,
            modifier = Modifier,
            fontSize = 14.sp
        )
    }

}
@Composable
fun LoginForm(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Faculty ID",
            color = Color(0xFF004020),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            placeholder = { Text("Enter your faculty ID", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.id_card),
                    contentDescription = "Email Icon",
                    tint = Color(0xFF004020),
                    modifier = Modifier.size(30.dp)
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Password",
            color = Color(0xFF004020),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        var passwordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = { Text("........", color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Email Icon",
                    tint = Color(0xFF004020)
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) painterResource(R.drawable.visibility) else painterResource(R.drawable.visibility_off)

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Toggle Password Visibility",
                        tint = Color(0xFF9E9E9E)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

/*@Composable
fun ForgotPasswordButton(onForgotClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        TextButton(onClick = onForgotClick) {
            Text(
                text = "Forgot Password?",
                color = Color(0xFF004020),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}*/

@Composable
fun LoginActionButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF004020),
            contentColor = Color.White
        )
    ) {
        Text(
            text = "SIGN IN",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.login),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}


@Composable
fun QuickAccessSection(onBiometricClick: () -> Unit) {
    // State to track if the user is currently "scanning" (holding)
    var isScanning by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = Color.LightGray
            )
            Text(
                text = "Quick Access",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.Gray,
                fontSize = 12.sp
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(80.dp)
                .drawBehind {
                    drawCircle(
                        // If scanning, the circle turns green
                        color = if (isScanning) Color(0xFF004020) else Color.LightGray,
                        style = Stroke(
                            width = 3.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(
                                intervals = floatArrayOf(10f, 10f),
                                phase = 0f
                            )
                        )
                    )
                }
                // --- BIOMETRIC HOLD LOGIC ---
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            // 1. Wait for the initial touch
                            val down = awaitFirstDown()
                            isScanning = true

                            var holdCompleted = false

                            // 2. Start a background timer for 500ms (half a second)
                            val timerJob = scope.launch {
                                delay(500L)
                                holdCompleted = true
                                onBiometricClick() // TRIGGER WHILE PRESSED
                            }

                            // 3. Watch for the finger lifting
                            waitForUpOrCancellation()

                            // 4. Cleanup: If they lift before 500ms, cancel the timer
                            timerJob.cancel()
                            isScanning = false

                            // If it already triggered, we can break or just let it reset
                            if (holdCompleted) break
                        }
                    }
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.fingerprint),
                contentDescription = "Biometric Login",
                // Icon turns green when you press down
                tint = if (isScanning) Color(0xFF004020) else Color.Gray,
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isScanning) "Scanning..." else "Hold to Sign In",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (isScanning) Color(0xFF004020) else Color.Black
        )

        Text(
            text = "Biometric Authentication",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun LoginFooter() {
    Surface(
        color = Color(0xFF004020),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "© 2024 COLEGIO DE ALICIA • ACADEMIC INTEGRITY",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
