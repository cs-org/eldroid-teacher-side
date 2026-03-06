package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        containerColor = Color.White,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // HOME
            BottomTabItem(
                label = "HOME",
                icon = Icons.Outlined.Home,
                isSelected = currentRoute == "dashboard",
                onClick = { navController.navigate("dashboard") }
            )

            // SCHEDULES
            BottomTabItem(
                label = "SCHEDULES",
                icon = Icons.Outlined.DateRange,
                isSelected = currentRoute == "schedule",
                onClick = { navController.navigate("schedule") }
            )

            // GRADES
            BottomTabItem(
                label = "GRADES",
                painter = painterResource(R.drawable.grad_hat),
                isSelected = currentRoute == "grades",
                onClick = { navController.navigate("grades") }
            )

            // PROFILE
            BottomTabItem(
                label = "PROFILE",
                icon = Icons.Outlined.Person,
                isSelected = currentRoute == "profile",
                onClick = { navController.navigate("profile") }
            )
        }
    }
    }

@Composable
fun BottomTabItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector? = null,
    painter: androidx.compose.ui.graphics.painter.Painter? = null,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor = if (isSelected) Color(0xFF004020) else Color.Gray

    Column(
        modifier = Modifier.clickable(
            interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() },
            indication = null
        ) { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (icon != null) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(28.dp), tint = contentColor)
        } else if (painter != null) {
            Icon(painter = painter, contentDescription = null, modifier = Modifier.size(28.dp), tint = contentColor)
        }

        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
            color = contentColor
        )
    }
}
