package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.data.ChatData
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.MessageUI

@Composable
fun MessageScreen(navController: NavController) {
    // State for searching parents
    var searchQuery by remember { mutableStateOf("") }

    val directChats = listOf(
        ChatData("Mrs. Santerna", "Andrea's Mother", "I'll be late for the pick up today.", "2 min ago", 1, true),
        ChatData("Mr. Lacorte", "Daryl's Father", "Thank you for the update on the project.", "10:30 AM"),
        ChatData("Mr. Amaya", "Carl's Father", "Will there be a field trip form sent home.", "Yesterday"),
        ChatData("Mr. Carbajal", "Albert's Father", "Regarding the math homework from last n...", "Yesterday"),
        ChatData("Mrs. Mata", "Beryl's Mother", "Thank you for the update on the project.", "10:30 AM"),
        ChatData("Mr. Galagar", "Mark's Father", "Thank you for the update on the project.", "10:30 AM")
    )

    BaseScreen(
        title = "Messages",
        subtitle = "Faculty Portal", // Added per your latest code
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.navigate("dashboard") }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF004020)
                )
            }
        },
        actions = { Icon(Icons.Default.Settings, contentDescription = null) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search parents...", color = Color.Gray) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.Gray
                    )
                },                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color(0xFFE0E0E0),
                    focusedBorderColor = Color(0xFF004020)
                )
            )

            // Direct List of Parents
            LazyColumn {
                items(directChats.filter { it.name.contains(searchQuery, ignoreCase = true) }) { chat ->
                    MessageUI(chat)
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFFF0F0F0)
                    )
                }
            }
        }
    }
}