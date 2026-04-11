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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eldroid_teacher_side.ui.components.AnimatedBottomBar
import com.example.eldroid_teacher_side.ui.components.BottomNavItems
import com.example.eldroid_teacher_side.ui.components.ProfileDrawerContent
import com.example.eldroid_teacher_side.ui.screens.*
import com.example.eldroid_teacher_side.ui.theme.EldroidteachersideTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eldroid_teacher_side.viewmodels.CourseStudentsViewModel

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

    // Initialize the SHARED ViewModel here
    val courseViewModel: CourseStudentsViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("main_content") {
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val infinitePageCount = Int.MAX_VALUE
            val initialPage = (infinitePageCount / 2) - ((infinitePageCount / 2) % BottomNavItems.size) + 2

            val pagerState = rememberPagerState(
                initialPage = initialPage,
                pageCount = { infinitePageCount }
            )

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        ProfileDrawerContent(
                            navController = navController,
                            onLogout = {
                                sharedPrefs.edit().putBoolean("is_logged_in", false).apply()
                                navController.navigate("login") { popUpTo("main_content") { inclusive = true } }
                            },
                            onCloseDrawer = { scope.launch { drawerState.close() } }
                        )
                    }
                }
            ) {
                Scaffold(
                    bottomBar = { AnimatedBottomBar(navController, pagerState) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize(),
                            beyondViewportPageCount = 2
                        ) { page ->
                            val actualIndex = page % BottomNavItems.size
                            when (BottomNavItems[actualIndex].route) {

                                "grades" -> GradeScreen(
                                    navController = navController,
                                    isDarkMode = isDarkMode,
                                    onThemeToggle = onThemeToggle,
                                    onOpenDrawer = { scope.launch { drawerState.open() } },
                                    viewModel = courseViewModel // Pass shared VM
                                )

                                "dashboard" -> DashboardScreen(
                                    navController = navController,
                                    isDarkMode = isDarkMode,
                                    onThemeToggle = onThemeToggle,
                                    onOpenDrawer = { scope.launch { drawerState.open() } },
                                    onNavigateToAttendance = { course ->
                                        // 1. Update the shared VM with the clicked course
                                        courseViewModel.selectCourse(course)
                                        // 2. Scroll to the Attendance Tab (Index 3)
                                        scope.launch {
                                            val currentActual = pagerState.currentPage % BottomNavItems.size
                                            pagerState.animateScrollToPage(pagerState.currentPage + (3 - currentActual))
                                        }
                                    },
                                    onNavigateToGrades = { course ->
                                        // 1. Update the shared VM
                                        courseViewModel.selectCourse(course)
                                        // 2. Scroll to the Grades Tab (Index 1)
                                        scope.launch {
                                            val currentActual = pagerState.currentPage % BottomNavItems.size
                                            pagerState.animateScrollToPage(pagerState.currentPage + (1 - currentActual))
                                        }
                                    }
                                )

                                "attendance" -> AttendanceScreen(
                                    navController = navController,
                                    isDarkMode = isDarkMode,
                                    onThemeToggle = onThemeToggle,
                                    onOpenDrawer = { scope.launch { drawerState.open() } },
                                    viewModel = courseViewModel // Pass shared VM
                                )
                                "messages" -> MessageScreen(
                                    navController = navController,
                                    isDarkMode = isDarkMode,
                                    onThemeToggle = onThemeToggle,
                                    onOpenDrawer = { scope.launch { drawerState.open() } }
                                )
                                "schedule" -> ScheduleScreen(
                                    navController = navController,
                                    isDarkMode = isDarkMode,
                                    onThemeToggle = onThemeToggle,
                                    onOpenDrawer = { scope.launch { drawerState.open() } }
                                    // viewModel will be provided automatically by the compose-viewmodel library
                                )
                            }
                        }
                    }
                }
            }
        }

        composable("login") {
            LoginScreen(navController = navController) { userData ->
                // SAVE TO SHARED PREFERENCES
                sharedPrefs.edit().apply {
                    putBoolean("is_logged_in", true)
                    putString("faculty_id", userData.facultyId)
                    putString("full_name", userData.fullName)
                    putString("email", userData.email)

                    putString("profile_image", userData.profileImage)
                    apply()
                }

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
        composable("personal_information") { PersonalInformationScreen(navController = navController) }
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
