package com.example.eldroid_teacher_side.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlin.math.abs

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val BottomNavItems = listOf(
    BottomNavItem("schedule", Icons.Outlined.DateRange, "Schedules"),
    BottomNavItem("grades", Icons.Outlined.Star, "Grades"),
    BottomNavItem("dashboard", Icons.Outlined.Home, "Dashboard"),
    BottomNavItem("attendance", Icons.Outlined.CheckCircle, "Attendance"),
    BottomNavItem("messages", Icons.Outlined.Email, "Messages")
)

@Composable
fun AnimatedBottomBar(
    navController: NavController,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()
    val itemCount = BottomNavItems.size

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomStart
    ) {
        val barWidth = maxWidth
        val itemWidth = barWidth / itemCount
        
        // Map infinite pager progress to the local 0..itemCount range
        val currentProgress by remember {
            derivedStateOf {
                val actualIndex = pagerState.currentPage % itemCount
                actualIndex + pagerState.currentPageOffsetFraction
            }
        }

        // Handle the wrap-around for the floating bubble
        // When progress goes beyond itemCount - 1, we want it to wrap back to 0
        val displayProgress = if (currentProgress >= itemCount - 0.5f && pagerState.currentPageOffsetFraction > 0.5f) {
             // As we swipe past the last item, the bubble should appear to come from the left or head to the right
             currentProgress
        } else {
            currentProgress
        }
        
        // We calculate floatingX based on modded progress. 
        // Note: (currentProgress % itemCount) handles the wrap-around visually.
        val floatingX = itemWidth * ((currentProgress % itemCount) + 0.5f)

        // Main Navigation Bar Surface
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            shape = RoundedCornerShape(35.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItems.forEachIndexed { index, item ->
                    // Distance calculation needs to account for circularity
                    val distance = remember(currentProgress, index) {
                        val d = abs((currentProgress % itemCount) - index)
                        // Account for the wrap-around distance (e.g. distance between 0 and 4 is 1)
                        minOf(d, itemCount - d)
                    }
                    
                    val iconAlpha = (distance * 2f).coerceIn(0f, 1f)

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                scope.launch {
                                    // Navigate to the nearest "virtual" page for this index
                                    val currentActual = pagerState.currentPage % itemCount
                                    val diff = index - currentActual
                                    
                                    // Find shortest path in infinite pager
                                    val targetPage = if (abs(diff) <= itemCount / 2) {
                                        pagerState.currentPage + diff
                                    } else {
                                        if (diff > 0) pagerState.currentPage + diff - itemCount
                                        else pagerState.currentPage + diff + itemCount
                                    }
                                    
                                    pagerState.animateScrollToPage(targetPage)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .size(26.dp)
                                    .alpha(iconAlpha)
                            )
                            
                            if (distance < 0.5f) {
                                val dotAlpha = (1f - distance * 2f).coerceIn(0f, 1f)
                                Box(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = dotAlpha))
                                )
                            }
                        }
                    }
                }
            }
        }

        // The Floating Green Circular Container
        Box(
            modifier = Modifier
                .offset(x = floatingX - 35.dp, y = (-35).dp)
                .size(70.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            BottomNavItems.forEachIndexed { index, item ->
                val distance = run {
                    val d = abs((currentProgress % itemCount) - index)
                    minOf(d, itemCount - d)
                }

                if (distance < 1f) {
                    val alpha = (1f - distance).coerceIn(0f, 1f)
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(32.dp)
                            .alpha(alpha)
                    )
                }
            }
        }
    }
}
