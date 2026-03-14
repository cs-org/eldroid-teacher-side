package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.data.ChatData
import com.example.eldroid_teacher_side.ui.components.MessageUI

@Composable
fun MessageScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    val directChats = listOf(
        ChatData("Mrs. Santerna", "Andrea's Mother", "I'll be late for the pick up today.", "2 min ago", 1, true),
        ChatData("Mr. Lacorte", "Daryl's Father", "Thank you for the update on the project.", "10:30 AM"),
        ChatData("Mr. Amaya", "Carl's Father", "Will there be a field trip form sent home.", "Yesterday"),
        ChatData("Mr. Carbajal", "Albert's Father", "Regarding the math homework from last n...", "Yesterday"),
        ChatData("Mrs. Mata", "Beryl's Mother", "Thank you for the update on the project.", "10:30 AM"),
        ChatData("Mr. Galagar", "Mark's Father", "Thank you for the update on the project.", "10:30 AM")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header without back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Messages",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Faculty Portal",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search parents...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface
            )
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(directChats.filter { it.name.contains(searchQuery, ignoreCase = true) }) { chat ->
                MessageUI(chat = chat, onClick = {
                    navController.navigate("chat_detail/${chat.name}/${chat.role}")
                })
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
            }
        }
    }
}
