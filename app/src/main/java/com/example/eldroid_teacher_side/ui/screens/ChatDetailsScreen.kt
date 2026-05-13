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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.ChatBubble
import com.example.eldroid_teacher_side.viewmodels.ChatDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavController,
    parentName: String,
    receiverId: String,
    viewModel: ChatDetailViewModel = viewModel()
) {
    // UI State
    var textState by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    // Theme Colors (Slate & Emerald Palette)
    val primaryGreen = Color(0xFF1B3D2F)
    val backgroundWhite = Color.White

    // Backend & Interaction State
    val messages by viewModel.chatMessages.collectAsState()
    var showAttachSheet by remember { mutableStateOf(false) }
    var showActionSheet by remember { mutableStateOf(false) }
    var selectedMessageIndex by remember { mutableStateOf<Int?>(null) }
    var editingMessageIndex by remember { mutableStateOf<Int?>(null) }

    // Initialization: Load History & Listen to Sockets
    LaunchedEffect(receiverId) {
        viewModel.loadHistory(receiverId)
        viewModel.listenForIncoming(receiverId)
    }

    // Auto-scroll on new messages
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> /* File handling logic */ }

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
                    modifier = Modifier.clickable { showAttachSheet = false; filePickerLauncher.launch("application/pdf") }
                )
            }
        }
    }

    // --- 2. MESSAGE ACTION MENU ---
    if (showActionSheet) {
        ModalBottomSheet(
            onDismissRequest = { showActionSheet = false; selectedMessageIndex = null },
            containerColor = backgroundWhite,
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
                        // FIX: Use .message (or whatever field stores your text in MessageData)
                        textState = messages[selectedMessageIndex!!].message
                        showActionSheet = false
                    }
                }
                ActionIconButton(icon = Icons.Default.ContentCopy, label = "Copy", color = primaryGreen) { showActionSheet = false }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(parentName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Parent", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            Surface(tonalElevation = 2.dp, color = backgroundWhite) {
                Row(
                    modifier = Modifier.padding(12.dp).fillMaxWidth().navigationBarsPadding().imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showAttachSheet = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Attach")
                    }

                    OutlinedTextField(
                        value = textState,
                        onValueChange = { textState = it },
                        placeholder = { Text("Type a message...", fontSize = 14.sp) },
                        modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = Color.LightGray.copy(alpha = 0.3f),
                            focusedContainerColor = Color.LightGray.copy(alpha = 0.3f)
                        )
                    )

                    FloatingActionButton(
                        onClick = {
                            if (textState.isNotBlank()) {
                                // Logic: If editing, call update. If not, call send.
                                viewModel.sendMessage(receiverId, textState)
                                textState = ""
                                editingMessageIndex = null
                                focusManager.clearFocus()
                            }
                        },
                        containerColor = primaryGreen,
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
            modifier = Modifier.padding(padding).fillMaxSize().background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        "CONVERSATION STARTED",
                        fontSize = 10.sp,
                        modifier = Modifier
                            .background(Color.LightGray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            itemsIndexed(messages) { index, message ->
                ChatBubble(
                    message = message,
                    onLongPress = {
                        selectedMessageIndex = index
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
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, color = color, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}