package com.example.eldroid_teacher_side

import android.content.Context
import android.os.Build
import android.os.Bundle
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
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eldroid_teacher_side.network.ChatSocketHandler
import com.example.eldroid_teacher_side.network.RetrofitClient
import com.example.eldroid_teacher_side.network.TokenManager
import com.example.eldroid_teacher_side.ui.components.AnimatedBottomBar
import com.example.eldroid_teacher_side.ui.components.BottomNavItems
import com.example.eldroid_teacher_side.ui.components.ProfileDrawerContent
import com.example.eldroid_teacher_side.ui.screens.*
import com.example.eldroid_teacher_side.ui.theme.EldroidteachersideTheme
import com.example.eldroid_teacher_side.ui.theme.LocalThemeState
import com.example.eldroid_teacher_side.ui.theme.ThemeState
import com.example.eldroid_teacher_side.util.navigateSafe
import com.example.eldroid_teacher_side.viewmodels.CourseStudentsViewModel
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

// MainActivity must extend FragmentActivity for BiometricPrompt to work
class MainActivity : FragmentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use applicationContext to prevent memory leaks in singletons
        RetrofitClient.init(applicationContext)
        val tokenManager = TokenManager(applicationContext)

        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isLoggedInFlag = sharedPrefs.getBoolean("is_logged_in", false)
        val token = tokenManager.getToken()

        // Robust check: Only logged in if flag is true AND token exists
        val isLoggedIn = isLoggedInFlag && token != null

        if (isLoggedIn && token != null) {
            ChatSocketHandler.init(token)
            ChatSocketHandler.connect()
        }

        val startDestination = if (isLoggedIn) "main_content" else "login"

        enableEdgeToEdge()
        setContent {
            val systemInDarkTheme = isSystemInDarkTheme()
            
            // Persistence Fix: Load saved theme preference from SharedPreferences.
            // If no preference is saved, fall back to the system theme.
            var isDarkMode by remember { 
                mutableStateOf(sharedPrefs.getBoolean("is_dark_mode", systemInDarkTheme)) 
            }

            // Optional: Still react to system theme changes if the user hasn't set a manual preference
            // However, to keep it simple and respect the user's manual toggle, we update preference on toggle.
            
            val themeState = remember(isDarkMode){
                ThemeState(
                    isDarkMode = isDarkMode,
                    toggleTheme = { 
                        isDarkMode = !isDarkMode 
                        // Save the user's choice permanently
                        sharedPrefs.edit().putBoolean("is_dark_mode", isDarkMode).apply()
                    }
                )
            }

            CompositionLocalProvider(LocalThemeState provides themeState) {
                EldroidteachersideTheme(darkTheme = LocalThemeState.current.isDarkMode) {
                    MainScreen(startDestination = startDestination)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    startDestination: String
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }
    // Use applicationContext to prevent memory leaks
    val tokenManger = remember { TokenManager(context.applicationContext) }
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
                                // Safely disconnect socket on logout
                                ChatSocketHandler.disconnect()
                                navController.navigateSafe("login") { popUpTo("main_content") { inclusive = true } }
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
                                    onOpenDrawer = { scope.launch { drawerState.open() } },
                                    viewModel = courseViewModel // Pass shared VM
                                )

                                "dashboard" -> DashboardScreen(
                                    navController = navController,
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
                                    onOpenDrawer = { scope.launch { drawerState.open() } },
                                    viewModel = courseViewModel // Pass shared VM
                                )
                                "messages" -> MessageScreen(
                                    navController = navController,
                                    onOpenDrawer = { scope.launch { drawerState.open() } }
                                )
                                "schedule" -> ScheduleScreen(
                                    navController = navController,
                                    onOpenDrawer = { scope.launch { drawerState.open() } }
                                )
                            }
                        }
                    }
                }
            }
        }

        composable("login") {
            LoginScreen(navController = navController, tokenManager = tokenManger ) { userData ->
                // SAVE TO SHARED PREFERENCES
                sharedPrefs.edit().apply {
                    putBoolean("is_logged_in", true)
                    putString("faculty_id", userData.facultyId)
                    putString("full_name", userData.fullName)
                    putString("email", userData.email)

                    putString("profile_image", userData.profileImage)
                    apply()
                }

                tokenManger.getToken()?.let { token ->
                    ChatSocketHandler.init(token)
                    ChatSocketHandler.connect()
                }

                navController.navigateSafe("main_content") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        composable("profile") { ProfileScreen(navController, onLogout = {
            sharedPrefs.edit().putBoolean("is_logged_in", false).apply()
            tokenManger.clearToken()
            // Ensure socket is disconnected on logout
            ChatSocketHandler.disconnect()
            navController.navigateSafe("login") {
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
        // Inside MainActivity.kt NavHost
        composable(
            "chat_detail/{parentName}/{receiverId}",
            arguments = listOf(
                navArgument("parentName") { type = NavType.StringType },
                navArgument("receiverId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("parentName") ?: ""
            val id = backStackEntry.arguments?.getString("receiverId") ?: ""
            ChatDetailScreen(navController, name, id)
        }

        // Inside NavHost(navController = navController, startDestination = ...)
        composable("request_otp") { RequestOTPScreen(navController) }
        composable("verify_otp") { OTPVerificationScreen(navController) }
        composable("reset_password") { ResetPasswordScreen(navController) }
    }
}
