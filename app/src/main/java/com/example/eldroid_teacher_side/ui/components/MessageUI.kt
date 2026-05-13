package com.example.eldroid_teacher_side.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.ui.data.ChatData

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageUI(
    chat: ChatData,
    onClick: () -> Unit,
    onDelete: () -> Unit = {},
    onBlock: () -> Unit = {},
    onArchive: () -> Unit = {},
    onMute: () -> Unit = {}
) {
    // State for the Bottom Sheet
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // The Main Chat Item Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { showSheet = true } // Long press opens the sheet
            )
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Placeholder
        Box(modifier = Modifier.size(56.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            if (chat.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4CAF50))
                        .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }
        }

        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(text = chat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = chat.role, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            Text(
                text = chat.lastMessage,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(text = chat.time, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            if (chat.unreadCount > 0) {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.size(22.dp).padding(top = 4.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = chat.unreadCount.toString(), color = MaterialTheme.colorScheme.onPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // --- MODAL BOTTOM SHEET (Like your screenshot) ---
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() }, // The little horizontal bar at the top
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = "Chat Options",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B3D2F), // Matching the dark green in your image
                    modifier = Modifier.padding(bottom = 16.dp, start = 8.dp)
                )

                // Reuse a custom row component for the options
                SheetOptionItem(icon = Icons.Default.Delete, label = "Delete Chat",isDestructive = true, onClick = { onDelete(); showSheet = false })
                SheetOptionItem(icon = Icons.Default.Block, label = "Block Parent",isDestructive = true, onClick = { onBlock(); showSheet = false })
                SheetOptionItem(icon = Icons.Default.Archive, label = "Archive", onClick = { onArchive(); showSheet = false })
                SheetOptionItem(icon = Icons.Default.VolumeOff, label = "Mute Notifications", onClick = { onMute(); showSheet = false })
            }
        }
    }
}

@Composable
fun SheetOptionItem(
    icon: ImageVector,
    label: String,
    isDestructive: Boolean = false, // New parameter
    onClick: () -> Unit
) {

    val contentColor = if (isDestructive) Color(0xFFC62828) else Color(0xFF1B3D2F)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor, // Apply the color to the icon
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            fontSize = 16.sp,
            color = contentColor, // Apply the color to the icon
            fontWeight = FontWeight.Medium
        )
    }
}