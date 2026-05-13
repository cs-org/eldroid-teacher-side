package com.example.eldroid_teacher_side.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Reply
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.ChatBubble
import com.example.eldroid_teacher_side.ui.data.MessageData
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(navController: NavController, parentName: String, parentRole: String) {
    var textState by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    // States
    var showAttachSheet by remember { mutableStateOf(false) }
    var showActionSheet by remember { mutableStateOf(false) }
    var editingMessageIndex by remember { mutableStateOf<Int?>(null) }
    var selectedMessageIndex by remember { mutableStateOf<Int?>(null) }

    val messages = remember {
        mutableStateListOf(
            MessageData("Hello Sir, I wanted to check on progress in math...", "09:12 AM", false),
            MessageData("Hi Ma'am! He has actually been doing great...", "09:45 AM", true, isEdited = true),
            MessageData("That is wonderful to hear!", "10:02 AM", false),
            MessageData("Yes, definitely.", "10:15 AM", true)
        )
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            messages.add(MessageData("Attachment: ${it.lastPathSegment}", currentTime, true))
        }
    }

    // Colors to match your screenshots
    val primaryGreen = Color(0xFF1B3D2F)
    val backgroundWhite = Color.White

    // --- 1. ATTACHMENT MENU ---
    if (showAttachSheet) {
        ModalBottomSheet(
            onDismissRequest = { showAttachSheet = false },
            containerColor = backgroundWhite,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp, start = 16.dp, end = 16.dp)) {
                Text("Select Attachment", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = primaryGreen, modifier = Modifier.padding(bottom = 16.dp, start = 8.dp))
                ListItem(
                    headlineContent = { Text("Photos & Videos") },
                    leadingContent = { Icon(Icons.Default.Photo, contentDescription = null, tint = primaryGreen) },
                    modifier = Modifier.clickable { showAttachSheet = false; filePickerLauncher.launch("image/*") }
                )
                ListItem(
                    headlineContent = { Text("Documents & Files") },
                    leadingContent = { Icon(Icons.Default.AttachFile, contentDescription = null, tint = primaryGreen) },
                    modifier = Modifier.clickable { showAttachSheet = false; filePickerLauncher.launch("application/*") }
                )
            }
        }
    }

    // --- 2. HORIZONTAL MESSAGE ACTION MENU (White & Green Style) ---
    if (showActionSheet) {
        ModalBottomSheet(
            onDismissRequest = { showActionSheet = false; selectedMessageIndex = null },
            containerColor = backgroundWhite,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 40.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionIconButton(icon = Icons.AutoMirrored.Filled.Reply, label = "Reply", color = primaryGreen) { showActionSheet = false }

                val isMine = selectedMessageIndex?.let { messages[it].isFromMe } ?: false
                if (isMine) {
                    ActionIconButton(icon = Icons.Default.Edit, label = "Edit", color = primaryGreen) {
                        editingMessageIndex = selectedMessageIndex
                        textState = messages[selectedMessageIndex!!].content
                        showActionSheet = false
                    }
                }

                ActionIconButton(icon = Icons.Default.ContentCopy, label = "Copy", color = primaryGreen) { showActionSheet = false }
                ActionIconButton(icon = Icons.Default.MoreHoriz, label = "More", color = primaryGreen) { showActionSheet = false }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(parentName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(parentRole, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            Surface(tonalElevation = 20.dp, color = Color.White){
                Column {
                    if (editingMessageIndex != null) {
                        Row(
                            modifier = Modifier.fillMaxWidth().background(primaryGreen.copy(alpha = 0.1f)).padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Editing message", fontSize = 12.sp, color = primaryGreen, modifier = Modifier.padding(start = 8.dp))
                            IconButton(onClick = { editingMessageIndex = null; textState = "" }, modifier = Modifier.size(24.dp)) {
                                Icon(Icons.Default.Close, contentDescription = null, tint = primaryGreen)
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth().navigationBarsPadding().imePadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { showAttachSheet = true }) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }

                        OutlinedTextField(
                            value = textState,
                            onValueChange = { textState = it },
                            placeholder = { Text("Type a message...", fontSize = 14.sp) },
                            modifier = Modifier.weight(1f).padding(horizontal = 8.dp).heightIn(min = 40.dp, max = 50.dp),
                            shape = RoundedCornerShape(24.dp),
                            maxLines = 5,
                            singleLine = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        )

                        FloatingActionButton(
                            onClick = {
                                if (textState.isNotBlank()) {
                                    if (editingMessageIndex != null) {
                                        val index = editingMessageIndex!!
                                        messages[index] = messages[index].copy(content = textState, isEdited = true)
                                        editingMessageIndex = null
                                    } else {
                                        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                                        messages.add(MessageData(textState, currentTime, true))
                                    }
                                    textState = ""
                                    focusManager.clearFocus()
                                }
                            },
                            containerColor = primaryGreen,
                            contentColor = Color.White,
                            shape = CircleShape,
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(imageVector = if (editingMessageIndex != null) Icons.Default.Check else Icons.AutoMirrored.Filled.Send, contentDescription = null)
                        }
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(padding).fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(messages) { index, message ->
                ChatBubble(
                    message = message,
                    onLongPress = {
                        selectedMessageIndex = index
                        focusManager.clearFocus()
                        showActionSheet = true
                    }
                )
            }
        }
    }
}

@Composable
fun ActionIconButton(icon: ImageVector, label: String, color: Color, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clip(RoundedCornerShape(8.dp)).clickable { onClick() }.padding(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = color, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, color = color, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}