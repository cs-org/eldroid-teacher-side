package com.example.eldroid_teacher_side.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.eldroid_teacher_side.ui.components.ChatBubble
import com.example.eldroid_teacher_side.ui.data.MessageData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(navController: NavController, parentName: String, parentRole: String) {
    var textState by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Bottom Sheet State
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    val messages = remember {
        mutableStateListOf(
            MessageData("Hello Sir, I wanted to check on my son/daughter's progress in math this week...", "09:12 AM", false),
            MessageData("Hi Ma'am! Please don't worry. He/She has actually been doing great...", "09:45 AM", true, true),
            MessageData("That is wonderful to hear! I'll make sure to praise his/her effort tonight...", "10:02 AM", false),
            MessageData("Yes, definitely. They are helping him/her build confidence.", "10:15 AM", true, true)
        )
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            messages.add(MessageData("Sent an attachment: ${it.lastPathSegment}", currentTime, true))
        }
    }

    // ATTACHMENT MENU (The Bottom Sheet)
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp, top = 8.dp)
            ) {
                Text(
                    "Select Attachment",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                ListItem(
                    headlineContent = { Text("Photos & Videos") },
                    leadingContent = { Icon(Icons.Default.Photo, contentDescription = null, tint = Color(0xFF1B3D2F)) },
                    modifier = Modifier.clickable {
                        showSheet = false
                        filePickerLauncher.launch("image/*")
                    }
                )
                ListItem(
                    headlineContent = { Text("Documents & Files") },
                    leadingContent = { Icon(Icons.Default.AttachFile, contentDescription = null, tint = Color(0xFF1B3D2F)) },
                    modifier = Modifier.clickable {
                        showSheet = false
                        filePickerLauncher.launch("application/pdf") // or "*/*" for all files
                    }
                )
            }
        }
    }

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
            Surface(tonalElevation = 2.dp) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Open the Bottom Sheet instead of direct launch
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Attach", tint = Color.Gray)
                    }

                    OutlinedTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        placeholder = { Text("Type a message...", fontSize = 14.sp) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        )
                    )

                    FloatingActionButton(
                        onClick = {
                            if (textState.isNotBlank()) {
                                val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                                messages.add(MessageData(textState, currentTime, true))
                                textState = ""
                            }
                        },
                        containerColor = Color(0xFF1B3D2F),
                        contentColor = Color.White,
                        shape = CircleShape,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFFDFDFD)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        "TODAY",
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier
                            .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            items(messages) { message ->
                ChatBubble(message)
            }
        }

        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }
}
