package com.example.eldroid_teacher_side.ui.data

data class MessageData(
    val content: String,
    val time: String,
    val isFromMe: Boolean, // True = Teacher (Green bubble), False = Parent (White bubble)
    val isRead: Boolean = false
)