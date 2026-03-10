package com.example.eldroid_teacher_side.ui.data

data class ChatData(
    val name: String,
    val role: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false
)