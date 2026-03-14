package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    val messageContent: @Composable () -> Unit // Using a composable so we can easily bold specific words
)

@Composable
fun NotificationScreen(navController: NavController) {
    val darkGreen = Color(0xFF1B3D2F)
    val readBgColor = Color.White
    val unreadBgColor = Color(0xFFF4FAF6) // Very light green tint
    val unreadDotColor = Color(0xFF00C853) // Bright green

    // Icon Colors
    val yellowBg = Color(0xFFFFF4E5)
    val yellowIcon = Color(0xFFE6A020)

    val redBg = Color(0xFFFFEBEE)
    val redIcon = Color(0xFFD32F2F)

    val blueBg = Color(0xFFE3F2FD)
    val blueIcon = Color(0xFF1976D2)

    val grayBg = Color(0xFFF5F5F5)
    val grayIcon = Color(0xFF616161)

    // State holding our list of notifications
    var notifications by remember {
        mutableStateOf(
            listOf(
                NotificationItemData(
                    id = 1,
                    title = "Academic Deadline",
                    time = "2h ago",
                    icon = Icons.Outlined.Schedule,
                    iconBgColor = yellowBg,
                    iconColor = yellowIcon,
                    isUnread = true, // Starts unread
                    category = "TODAY",
                    messageContent = {
                        Text(
                            buildAnnotatedString {
                                append("Grade submission deadline for ")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = darkGreen)) {
                                    append("CS101")
                                }
                                append(" is in 2 hours.")
                            },
                            fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp
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
                    isUnread = true, // Starts unread
                    category = "TODAY",
                    messageContent = {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.DarkGray)) {
                                    append("3 students")
                                }
                                append(" in your Data Structures class are at risk of failing. Check analytics for details.")
                            },
                            fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp
                        )
                    }
                ),
                NotificationItemData(
                    id = 3,
                    title = "Administrative Memo",
                    time = "Yesterday",
                    icon = Icons.Outlined.Description,
                    iconBgColor = blueBg,
                    iconColor = blueIcon,
                    isUnread = false, // Already read
                    category = "EARLIER",
                    messageContent = {
                        Text(
                            "New memo from the Dean regarding the upcoming faculty meeting in October.",
                            fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp
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
                    isUnread = false, // Already read
                    category = "EARLIER",
                    messageContent = {
                        Text(
                            "Security alert: New login detected from iPhone 14 Pro in Manila, PH.",
                            fontSize = 13.sp, color = Color.DarkGray, lineHeight = 18.sp
                        )
                    }
                )
            )
        )
    }

    BaseScreen(
        title = "Notifications",
        subtitle = "Stay updated", // Or leave empty if you prefer
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = darkGreen)
            }
        },
        actions = {
            // THE SIMULATION: Mark all as read button
            TextButton(
                onClick = {
                    // Update the state: change all notifications to isUnread = false
                    notifications = notifications.map { it.copy(isUnread = false) }
                }
            ) {
                Text(
                    text = "Mark all as read",
                    color = Color(0xFF2E7D32), // Darkish green
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
        ) {
            // Filter categories
            val todayNotifications = notifications.filter { it.category == "TODAY" }
            val earlierNotifications = notifications.filter { it.category == "EARLIER" }

            // TODAY SECTION
            if (todayNotifications.isNotEmpty()) {
                item {
                    SectionHeaderTitle("TODAY")
                }
                items(todayNotifications) { notification ->
                    NotificationRow(
                        notification = notification,
                        bgColor = if (notification.isUnread) unreadBgColor else readBgColor,
                        unreadDotColor = unreadDotColor
                    )
                    HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)
                }
            }

            // EARLIER SECTION
            if (earlierNotifications.isNotEmpty()) {
                item {
                    SectionHeaderTitle("EARLIER")
                }
                items(earlierNotifications) { notification ->
                    NotificationRow(
                        notification = notification,
                        bgColor = if (notification.isUnread) unreadBgColor else readBgColor,
                        unreadDotColor = unreadDotColor
                    )
                    HorizontalDivider(color = Color(0xFFF0F0F0), thickness = 1.dp)
                }
            }
        }
    }
}

// Reusable component for the small section headers
@Composable
fun SectionHeaderTitle(title: String) {
    Text(
        text = title,
        fontSize = 11.sp,
        fontWeight = FontWeight.ExtraBold,
        color = Color(0xFF7A8B85), // Greyish green
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

// Reusable component for each notification item
@Composable
fun NotificationRow(
    notification: NotificationItemData,
    bgColor: Color,
    unreadDotColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Left Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(notification.iconBgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = notification.icon,
                contentDescription = null,
                tint = notification.iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Content
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = notification.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Time & Unread Dot
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = notification.time,
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    if (notification.isUnread) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(unreadDotColor)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Render the specific message content (with bold words)
            notification.messageContent()
        }
    }
}