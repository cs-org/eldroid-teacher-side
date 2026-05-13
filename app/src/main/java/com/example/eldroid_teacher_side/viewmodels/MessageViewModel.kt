package com.example.eldroid_teacher_side.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.network.ChatSocketHandler
import com.example.eldroid_teacher_side.network.RetrofitClient
import com.example.eldroid_teacher_side.ui.data.ChatData
import com.example.eldroid_teacher_side.ui.data.ChatMessageDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class MessageViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatData>>(emptyList())
    val messages: StateFlow<List<ChatData>> = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchMessages()
        setupSocketListeners()
    }

    private fun setupSocketListeners() {
        val socket = ChatSocketHandler.getSocket()

        // Inside setupSocketListeners in MessageViewModel.kt
        socket.on("receive_message") { args ->
            val data = args[0] as JSONObject

            val senderId = data.optString("sender_id", "")

            // Fallback chain to catch any variation of the name key
            val senderName = when {
                data.has("sender_name") -> data.getString("sender_name")
                data.has("parentName") -> data.getString("parentName")
                data.has("fullName") -> data.getString("fullName")
                else -> "Parent"
            }

            val preview = data.optString("message", "")
            updateInboxWithNewMessage(senderId, senderName, preview)
        }
    }

    private fun updateInboxWithNewMessage(senderId: String, senderName: String, preview: String) {
        val currentList = _messages.value.toMutableList()
        val existingIndex = currentList.indexOfFirst { it.sender_id == senderId }

        if (existingIndex != -1) {
            // Use the name already stored in our list instead of the "Unknown" one from the socket
            val existingName = currentList[existingIndex].name
            val updatedChat = currentList[existingIndex].copy(
                name = existingName,
                lastMessage = preview,
                time = "Just now",
                unreadCount = currentList[existingIndex].unreadCount + 1
            )
            currentList.removeAt(existingIndex)
            currentList.add(0, updatedChat)
        } else {
            // If it's a brand new chat not in our list, we have to use the name provided
            currentList.add(0, ChatData(
                name = senderName, // This will be "Parent" or "Unknown" until refreshed
                role = "Parent",
                lastMessage = preview,
                time = "Just now",
                unreadCount = 1,
                isOnline = true,
                sender_id = senderId,
            ))
        }
        _messages.value = currentList
    }

    fun fetchMessages() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getMessages()
                // LOG 1: Check if the API actually sent anything back
                android.util.Log.d("CHAT_DEBUG", "API Status: ${response.status}, Count: ${response.data.size}")
// Inside MessageViewModel.kt -> fetchMessages()
                val fetchedChats = response.data.map { db ->
                    ChatData(
                        // Simply use whatever name the backend provided
                        name = db.sender_name ?: "Unknown Parent",
                        role = "Parent",
                        lastMessage = db.message,
                        time = "Just now",
                        unreadCount = 0,
                        isOnline = false,
                        sender_id = db.sender_id
                    )
                }
// This updates the UI state with the list from the database
                _messages.value = fetchedChats
            } catch (e: Exception) {
                android.util.Log.e("CHAT_DEBUG", "Fetch Failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        // Optional: you can disconnect here, but usually better to do it
        // in the Activity/Fragment lifecycle so the socket stays alive during rotations.
    }
}