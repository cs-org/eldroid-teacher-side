package com.example.eldroid_teacher_side.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness2
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.eldroid_teacher_side.R

@Composable
fun ThemeToggleButton(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    val trackColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFFD1D1D1) else Color(0xFFEEEEEE),
        label = "trackColor"
    )
    val thumbColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF424242) else Color.White,
        label = "thumbColor"
    )
    val iconColor by animateColorAsState(
        targetValue = if (isDarkMode) Color.White else Color(0xFFFFD54F),
        label = "iconColor"
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (isDarkMode) 24.dp else 0.dp,
        label = "offset"
    )

    Box(
        modifier = Modifier
            .width(56.dp)
            .height(32.dp)
            .clip(CircleShape)
            .background(trackColor)
            .clickable { onThemeToggle() }
            .padding(4.dp)
    ) {
        Surface(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(24.dp),
            shape = CircleShape,
            color = thumbColor,
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = if (isDarkMode) Icons.Default.NightsStay else Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
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
    val contentColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

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
