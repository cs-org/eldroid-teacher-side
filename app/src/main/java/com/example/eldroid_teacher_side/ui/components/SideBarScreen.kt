package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.R

@Composable
fun NavigationDrawerContent(
    onDestinationClicked: (route: String) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
        modifier = Modifier.width(300.dp)
    ) {
        // Header Profile
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDestinationClicked("profile") }
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.professor),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF004020), CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Prof. Reyes",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B3D2F)
                )
                Text(
                    text = "Faculty ID: 2023-00154",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            HorizontalDivider(Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

            // Menu Items
            DrawerMenuItem("Dashboard", Icons.Outlined.Home) { onDestinationClicked("dashboard") }
            DrawerMenuItem("Schedules", Icons.Outlined.DateRange) { onDestinationClicked("schedule") }
            DrawerMenuItem("Grades", Icons.Outlined.Star) { onDestinationClicked("grades") }
            DrawerMenuItem("Attendance", Icons.Outlined.CheckCircle) { onDestinationClicked("attendance") }
            DrawerMenuItem("Messages", Icons.Outlined.Email) { onDestinationClicked("messages") }

            Spacer(modifier = Modifier.weight(1f))

            DrawerMenuItem("Logout", Icons.Outlined.ExitToApp, isDestructive = true) {
                onDestinationClicked("login")
            }
        }
    }
}

@Composable
fun DrawerMenuItem(
    label: String,
    icon: ImageVector,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (isDestructive) Color.Red else Color(0xFF1B3D2F)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = color)
    }
}