package com.example.eldroid_teacher_side.ui.data

data class ChatData(
    val name: String,
    val role: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
    val isOnline: Boolean,
    val sender_id: String,
)

data class MessageResponseWrapper(
    val status: String,
    val data: List<ChatMessageDb> // This ensures 'data' is a list of your DB rows
)
data class ChatHistoryResponse(
    val status: String,
    val data: List<ChatMessageDb>
)

data class ChatMessageDb(
    val id: Int,
    val sender_id: String,
    val receiver_id: String,
    val message: String,
    val sender_type: String,
    val created_at: String,
    val sender_name: String? = null // <--- ADD THIS LINE
)