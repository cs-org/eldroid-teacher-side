package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BaseScreen(
    title: String,
    subtitle: String,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    navController: NavController,
    showBottomBar: Boolean = true,
    pagerState: PagerState? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (title.isNotEmpty() || subtitle.isNotEmpty()) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 3.dp
                ) {
                    Column {
                        // Spacer for status bar/camera cutout
                        Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
                        
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (navigationIcon != null) {
                                    navigationIcon()
                                }
                                Spacer(Modifier.width(if (navigationIcon != null) 16.dp else 0.dp))

                                Column {
                                    Text(
                                        text = title,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = subtitle,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                actions?.invoke(this)
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            if (bottomBar != null) {
                bottomBar()
            } else if (showBottomBar && pagerState != null) {
                AnimatedBottomBar(navController, pagerState)
            }
        },
        contentWindowInsets = WindowInsets.systemBars,
        content = { padding ->
            content(padding)
        }
    )
}
