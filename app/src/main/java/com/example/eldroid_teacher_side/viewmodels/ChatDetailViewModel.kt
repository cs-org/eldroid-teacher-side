package com.example.eldroid_teacher_side.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.network.ChatSocketHandler
import com.example.eldroid_teacher_side.network.RetrofitClient
import com.example.eldroid_teacher_side.ui.data.MessageData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class ChatDetailViewModel : ViewModel() {
    private val _chatMessages = MutableStateFlow<List<MessageData>>(emptyList())
    val chatMessages: StateFlow<List<MessageData>> = _chatMessages

    // Inside ChatDetailViewModel.kt
    // Inside ChatDetailViewModel.kt
    fun loadHistory(receiverId: String) {
        viewModelScope.launch {
            try {
                // This now returns the ChatHistoryResponse object
                val response = RetrofitClient.apiService.getChatHistory(receiverId)

                // Access the list inside the 'data' field
                val mappedMessages = response.data.map { db ->
                    MessageData(
                        id = db.id,
                        sender_id = db.sender_id,
                        receiver_id = db.receiver_id,
                        message = db.message,
                        sender_type = db.sender_type,
                        created_at = db.created_at,
                        isFromMe = db.sender_type == "faculty"
                    )
                }
                _chatMessages.value = mappedMessages
            } catch (e: Exception) {
                e.printStackTrace()
                android.util.Log.e("CHAT_ERROR", "Failed to load: ${e.message}")
            }
        }
    }

    fun sendMessage(receiverId: String, text: String) {
        val messageObject = JSONObject().apply {
            put("receiver_id", receiverId)
            put("message", text)
            put("sender_type", "faculty")
        }
        // Emit 'send_message' (matches backend socket logic)
        // Use safe call ?. to prevent crashes if socket isn't initialized
        ChatSocketHandler.getSocket()?.emit("send_message", messageObject)
    }

    // Inside ChatDetailViewModel.kt
    fun listenForIncoming(currentChatPartnerId: String) {
        val socket = ChatSocketHandler.getSocket() ?: return

        // Listen for messages from the OTHER person
        socket.on("receive_message") { args ->
            val data = args[0] as JSONObject
            if (data.optString("sender_id") == currentChatPartnerId) {
                val newMessage = parseJsonToMessage(data)
                _chatMessages.value += newMessage
            }
        }

        // Listen for confirmation of YOUR messages
        socket.on("message_sent") { args ->
            val data = args[0] as JSONObject
            val newMessage = parseJsonToMessage(data)
            // Only add if not already there to prevent duplicates
            if (!_chatMessages.value.any { it.message == newMessage.message && it.created_at == "Just now" }) {
                _chatMessages.value += newMessage
            }
        }
    }

    // 4. Added missing helper function
    // Inside ChatDetailViewModel.kt
    private fun parseJsonToMessage(data: JSONObject): MessageData {
        return MessageData(
            id = data.optInt("id", 0), // Include ID if your MessageData needs it
            sender_id = data.optString("sender_id", ""),
            receiver_id = data.optString("receiver_id", ""),
            message = data.optString("message", ""),
            sender_type = data.optString("sender_type", ""),
            created_at = "Just now",
            isFromMe = data.optString("sender_type") == "faculty"
        )
    }
}