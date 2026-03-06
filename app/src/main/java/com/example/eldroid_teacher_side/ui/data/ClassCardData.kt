package com.example.eldroid_teacher_side.ui.data

import androidx.compose.ui.graphics.Color

data class ClassCardData(
    val title: String,
    val room: String,
    val time: String,
    val status: String? = null, // e.g., "ONGOING NOW"
    val backgroundColor: Color = Color.Companion.White,
    val contentColor: Color = Color(0xFF004020),
    val buttonColor: Color = Color(0xFF004020),
    val showWatermark: Boolean = false
)