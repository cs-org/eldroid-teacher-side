package com.example.eldroid_teacher_side

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eldroid_teacher_side.ui.components.AnimatedBottomBar
import com.example.eldroid_teacher_side.ui.components.BottomNavItems
import com.example.eldroid_teacher_side.ui.screens.*
import com.example.eldroid_teacher_side.ui.theme.EldroidteachersideTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Move Auth Check to onCreate
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("is_logged_in", false)
        val startDestination = if (isLoggedIn) "main_content" else "login"

        enableEdgeToEdge()
        setContent {
            val systemInDarkTheme = isSystemInDarkTheme()
            var isDarkMode by remember { mutableStateOf(systemInDarkTheme) }

            EldroidteachersideTheme(darkTheme = isDarkMode) {
                MainScreen(
                    startDestination = startDestination,
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
    startDestination: String,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("main_content") {
            // Move Pager State initialization inside the destination to avoid initializing it when not logged in
            val infinitePageCount = Int.MAX_VALUE
            val initialPage = (infinitePageCount / 2) - ((infinitePageCount / 2) % BottomNavItems.size) + 2 // Start at Dashboard (index 2)
            
            val pagerState = rememberPagerState(
                initialPage = initialPage,
                pageCount = { infinitePageCount }
            )
            val scope = rememberCoroutineScope()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    AnimatedBottomBar(navController, pagerState)
                },
                contentWindowInsets = WindowInsets.systemBars // Respect system bars
            ) { innerPadding ->
                // Apply the innerPadding which includes status bar and navigation bar insets
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
                            "dashboard" -> DashboardScreen(
                                navController = navController,
                                isDarkMode = isDarkMode,
                                onThemeToggle = onThemeToggle,
                                onNavigateToAttendance = {
                                    scope.launch {
                                        val currentActual = pagerState.currentPage % BottomNavItems.size
                                        val targetIndex = 3 // attendance
                                        pagerState.animateScrollToPage(pagerState.currentPage + (targetIndex - currentActual))
                                    }
                                },
                                onNavigateToGrades = {
                                    scope.launch {
                                        val currentActual = pagerState.currentPage % BottomNavItems.size
                                        val targetIndex = 1 // grades
                                        pagerState.animateScrollToPage(pagerState.currentPage + (targetIndex - currentActual))
                                    }
                                }
                            )
                            "attendance" -> AttendanceScreen(navController)
                            "messages" -> MessageScreen(navController)
                        }
                    }
                }
            }
        }
        
        composable("login") { 
            LoginScreen(navController = navController) {
                // On login success, update persist it and navigate
                sharedPrefs.edit().putBoolean("is_logged_in", true).apply()
                navController.navigate("main_content") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        composable("profile") { ProfileScreen(navController, onLogout = {
            sharedPrefs.edit().putBoolean("is_logged_in", false).apply()
            navController.navigate("login") {
                popUpTo("main_content") { inclusive = true }
            }
        }) }
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
