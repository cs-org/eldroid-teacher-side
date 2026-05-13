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
import com.example.eldroid_teacher_side.ui.data.MessageData
import com.example.eldroid_teacher_side.viewmodels.ChatDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    navController: NavController,
    parentName: String,
    receiverId: String, // Changed from parentRole to receiverId to match MainActivity
    viewModel: ChatDetailViewModel = viewModel() // Connect your logic
) {
    var textState by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    // Get live data from the ViewModel
    val messages by viewModel.chatMessages.collectAsState()

    // Bottom Sheet State
    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    // Initialize logic: Load history and start listening for live socket updates
    LaunchedEffect(receiverId) {
        viewModel.loadHistory(receiverId)
        viewModel.listenForIncoming(receiverId)
    }

    // Auto-scroll to bottom when a new message arrives
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // Handle file logic here if needed
    }

    if (showSheet) {
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
                    leadingContent = { Icon(Icons.Default.AttachFile, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.clickable {
                        showSheet = false
                        filePickerLauncher.launch("application/pdf")
                    }
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
            Surface(
                tonalElevation = 2.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { showSheet = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Attach", tint = MaterialTheme.colorScheme.onSurfaceVariant)
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
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )

                    FloatingActionButton(
                        onClick = {
                            if (textState.isNotBlank()) {
                                // Send via Socket/API through ViewModel
                                viewModel.sendMessage(receiverId, textState)
                                textState = ""
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
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
            modifier = Modifier.padding(padding).fillMaxSize().background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        "CONVERSATION STARTED",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            // Render actual chat bubbles from the database/socket
            items(messages) { message ->
                ChatBubble(message)
            }
        }
    }
}