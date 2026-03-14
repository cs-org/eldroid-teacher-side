package com.example.eldroid_teacher_side

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eldroid_teacher_side.ui.screens.AttendanceScreen
import com.example.eldroid_teacher_side.ui.screens.DashboardScreen
import com.example.eldroid_teacher_side.ui.screens.GradeScreen
import com.example.eldroid_teacher_side.ui.screens.LoginScreen
import com.example.eldroid_teacher_side.ui.screens.ProfileScreen
import com.example.eldroid_teacher_side.ui.screens.ScheduleScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eldroid_teacher_side.ui.screens.AttendanceScreen
import com.example.eldroid_teacher_side.ui.screens.ChatDetailScreen
import com.example.eldroid_teacher_side.ui.screens.FAQScreen
import com.example.eldroid_teacher_side.ui.screens.LoginScreen
import com.example.eldroid_teacher_side.ui.screens.MessageScreen
import com.example.eldroid_teacher_side.ui.theme.EldroidteachersideTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemInDarkTheme = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemInDarkTheme) }

            EldroidteachersideTheme(darkTheme = isDarkMode) {
                MainScreen(
                    isDarkMode = isDarkMode,
                    onThemeToggle = { isDarkMode = !isDarkMode }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") {
                DashboardScreen(navController, isDarkMode, onThemeToggle)
            }
            composable("schedule") { ScheduleScreen(navController) }
            composable("grades") { GradeScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("attendance") {
                AttendanceScreen(navController)
            }
            composable("login") { LoginScreen(navController) }
            composable("messages") { MessageScreen(navController) }
            composable("faq") { FAQScreen(navController = navController) }
            composable("chat_detail/{name}/{role}") { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val role = backStackEntry.arguments?.getString("role") ?: ""
                ChatDetailScreen(navController, name, role)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EldroidteachersideTheme {
        MainScreen(isDarkMode = false, onThemeToggle = {})
    }
}
