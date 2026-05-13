package com.example.eldroid_teacher_side.ui.data

data class MessageData(
    val id: Int? = null,           // From your SERIAL PRIMARY KEY
    val sender_id: String,         // To identify who sent it
    val receiver_id: String,       // To identify who gets it
    val message: String,           // Renamed from 'content' to match your SQL schema
    val sender_type: String,       // 'faculty' or 'parent'
    val created_at: String,        // The timestamp from Postgres

    // UI Helpers (Not usually sent by the server, but used by the App)
    val isFromMe: Boolean = false  // Logic: true if sender_id == current_user_id
)