package com.example.eldroid_teacher_side

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eldroid_teacher_side.ui.components.AnimatedBottomBar
import com.example.eldroid_teacher_side.ui.components.BottomNavItems
import com.example.eldroid_teacher_side.ui.screens.*
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
    
    // Set a very large page count for infinite scrolling
    val infinitePageCount = Int.MAX_VALUE
    val initialPage = (infinitePageCount / 2) - ((infinitePageCount / 2) % BottomNavItems.size) + 2 // Start at Dashboard (index 2)
    
    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { infinitePageCount }
    )

    NavHost(
        navController = navController,
        startDestination = "main_content"
    ) {
        composable("main_content") {
            Scaffold(
                bottomBar = {
                    AnimatedBottomBar(navController, pagerState)
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        beyondViewportPageCount = 2
                    ) { page ->
                        val actualIndex = page % BottomNavItems.size
                        when (BottomNavItems[actualIndex].route) {
                            "schedule" -> ScheduleScreen(navController)
                            "grades" -> GradeScreen(navController)
                            "dashboard" -> DashboardScreen(navController, isDarkMode, onThemeToggle)
                            "attendance" -> AttendanceScreen(navController)
                            "messages" -> MessageScreen(navController)
                        }
                    }
                }
            }
        }
        
        // Screens that should NOT show the Bottom Bar (Secondary Screens)
        composable("login") { LoginScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("personal_information") { PersonalInformationScreen(navController) }
        composable("academic_credential") { AcademicCredentialScreen(navController) }
        composable("department_settings") { DepartmentSettingsScreen(navController) }
        composable("security_privacy") { SecurityPrivacyScreen(navController) }
        composable("change_password") { ChangePasswordScreen(navController) }
        composable("notification") { NotificationScreen(navController) }
        composable("faq") { FAQScreen(navController = navController) }
        composable("chat_detail/{name}/{role}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val role = backStackEntry.arguments?.getString("role") ?: ""
            ChatDetailScreen(navController, name, role)
        }
    }
}
