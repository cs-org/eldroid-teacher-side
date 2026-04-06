package com.example.eldroid_teacher_side.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.network.RetrofitClient
import com.example.eldroid_teacher_side.ui.data.ChatData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatData>>(emptyList())
    val messages: StateFlow<List<ChatData>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMessages()
    }

    fun fetchMessages() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getMessages()
                if (response.status == "success") {
                    // Map the DB data to your ChatData class
                    _messages.value = response.data.map { db ->
                        ChatData(
                            name = db.sender_name,
                            role = db.student_relation,
                            lastMessage = db.message_preview,
                            time = db.received_time,
                            unreadCount = db.unread_count,
                            isOnline = db.unread_count > 0 // Logic: Online if they have unread messages
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}