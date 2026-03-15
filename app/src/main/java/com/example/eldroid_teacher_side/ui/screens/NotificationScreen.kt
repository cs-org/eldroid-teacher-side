package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.NotificationRow
import com.example.eldroid_teacher_side.ui.components.SectionHeaderTitle

// Data class to hold notification data
data class NotificationItemData(
    val id: Int,
    val title: String,
    val time: String,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconColor: Color,
    val isUnread: Boolean,
    val category: String, // "TODAY" or "EARLIER"
    val messageContent: @Composable () -> Unit
)

@Composable
fun NotificationScreen(navController: NavController) {
    val unreadBgColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
    val unreadDotColor = MaterialTheme.colorScheme.primary

    // Standardized palette colors
    val greenBg = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    val greenIcon = MaterialTheme.colorScheme.primary

    val orangeBg = Color(0xFFFFF4E5)
    val orangeIcon = Color(0xFFE6A020)

    val redBg = MaterialTheme.colorScheme.errorContainer
    val redIcon = MaterialTheme.colorScheme.error

    val grayBg = MaterialTheme.colorScheme.surfaceVariant
    val grayIcon = MaterialTheme.colorScheme.onSurfaceVariant

    // State holding our list of notifications
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationItemData(
                    id = 1,
                    title = "Academic Deadline",
                    time = "2h ago",
                    icon = Icons.Outlined.Schedule,
                    iconBgColor = orangeBg,
                    iconColor = orangeIcon,
                    isUnread = true,
                    category = "TODAY",
                    messageContent = {
                        Text(
                            buildAnnotatedString {
                                append("Grade submission deadline for ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)) {
                                    append("CS101")
                                }
                                append(" is in 2 hours.")
                            },
                            fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp
                        )
                    }
                ),
                NotificationItemData(
                    id = 2,
                    title = "Student Progress",
                    time = "5h ago",
                    icon = Icons.Outlined.ErrorOutline,
                    iconBgColor = redBg,
                    iconColor = redIcon,
                    isUnread = true,
                    category = "TODAY",
                    messageContent = {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)) {
                                    append("3 students")
                                }
                                append(" in your Data Structures class are at risk of failing. Check analytics for details.")
                            },
                            fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp
                        )
                    }
                ),
                NotificationItemData(
                    id = 3,
                    title = "Administrative Memo",
                    time = "Yesterday",
                    icon = Icons.Outlined.Description,
                    iconBgColor = greenBg,
                    iconColor = greenIcon,
                    isUnread = false,
                    category = "EARLIER",
                    messageContent = {
                        Text(
                            "New memo from the Dean regarding the upcoming faculty meeting in October.",
                            fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp
                        )
                    }
                ),
                NotificationItemData(
                    id = 4,
                    title = "Security Alert",
                    time = "2 days ago",
                    icon = Icons.Outlined.Lock,
                    iconBgColor = grayBg,
                    iconColor = grayIcon,
                    isUnread = false,
                    category = "EARLIER",
                    messageContent = {
                        Text(
                            "Security alert: New login detected from iPhone 14 Pro in Manila, PH.",
                            fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 18.sp
                        )
                    }
                )
            )
        )
    }

    BaseScreen(
        title = "Notifications",
        subtitle = "Stay updated",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
            }
        },
        actions = {
            TextButton(
                onClick = {
                    notifications = notifications.map { it.copy(isUnread = false) }
                }
            ) {
                Text(
                    text = "Mark all as read",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            val todayNotifications = notifications.filter { it.category == "TODAY" }
            val earlierNotifications = notifications.filter { it.category == "EARLIER" }

            if (todayNotifications.isNotEmpty()) {
                item { SectionHeaderTitle("TODAY") }
                items(todayNotifications) { notification ->
                    NotificationRow(
                        notification = notification,
                        bgColor = if (notification.isUnread) unreadBgColor else MaterialTheme.colorScheme.surface,
                        unreadDotColor = unreadDotColor
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), thickness = 1.dp)
                }
            }

            if (earlierNotifications.isNotEmpty()) {
                item { SectionHeaderTitle("EARLIER") }
                items(earlierNotifications) { notification ->
                    NotificationRow(
                        notification = notification,
                        bgColor = if (notification.isUnread) unreadBgColor else MaterialTheme.colorScheme.surface,
                        unreadDotColor = unreadDotColor
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), thickness = 1.dp)
                }
            }
        }
    }
}
