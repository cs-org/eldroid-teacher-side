package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.data.MessageData
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(navController: NavController, parentName: String, parentRole: String) {
    var textState by remember { mutableStateOf("") }

    // Sample conversation
    val messages = listOf(
        MessageData("Hello Sir, I wanted to check on my son/daughter's progress in math this week...", "09:12 AM", false),
        MessageData("Hi Ma'am! Please don't worry. He/She has actually been doing great...", "09:45 AM", true, true),
        MessageData("That is wonderful to hear! I'll make sure to praise his/her effort tonight...", "10:02 AM", false),
        MessageData("Yes, definitely. They are helping him/her build confidence.", "10:15 AM", true, true)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(parentName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(parentRole, fontSize = 12.sp, color = Color.Gray)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            // Message Input Bar
            Surface(tonalElevation = 2.dp) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Add attachment */ }) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray)
                    }
                    OutlinedTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        placeholder = { Text("Type a message...", fontSize = 14.sp) },
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        )
                    )
                    FloatingActionButton(
                        onClick = { /* Send message */ },
                        containerColor = Color(0xFF1B3D2F), // Dark Forest Green
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFFFDFDFD)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("TODAY", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
            items(messages) { message ->
                ChatBubble(message)
            }
        }
    }
}

@Composable
fun ChatBubble(message: MessageData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (message.isFromMe) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (message.isFromMe) Color(0xFF2D4B3B) else Color(0xFFF5F2F0),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (message.isFromMe) 16.dp else 0.dp,
                bottomEnd = if (message.isFromMe) 0.dp else 16.dp
            ),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.content,
                modifier = Modifier.padding(12.dp),
                color = if (message.isFromMe) Color.White else Color.Black,
                fontSize = 14.sp
            )
        }
        Text(
            text = message.time,
            fontSize = 10.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}