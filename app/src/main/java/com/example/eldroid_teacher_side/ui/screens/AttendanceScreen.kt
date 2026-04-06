package com.example.eldroid_teacher_side.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.AttendanceSearchBar
import com.example.eldroid_teacher_side.ui.components.AttendanceStudentCard
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.AttendanceCalendarHeader
import com.example.eldroid_teacher_side.viewmodels.CourseStudentsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: CourseStudentsViewModel
) {
    var searchQuery by remember { mutableStateOf("") }

    // Collect data from database
    val selectedCourse by viewModel.selectedCourse.collectAsState()
    val studentList by viewModel.studentGrades.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Attendance",
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onThemeToggle) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Dark/Light Mode",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { navController.navigate("notification") }) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.graduate),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    // DYNAMIC COURSE CODE
                    Text(
                        text = selectedCourse?.course_code ?: "...",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        AttendanceCalendarHeader()
        AttendanceSearchBar(query = searchQuery, onQueryChange = { searchQuery = it })

        // DYNAMIC STUDENT COUNT
        Text(
            text = "STUDENT LIST (${studentList.size})",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // DYNAMIC LIST FROM DATABASE
            val filteredList = studentList.filter {
                it.students.last_name.contains(searchQuery, ignoreCase = true) ||
                        it.students.first_name.contains(searchQuery, ignoreCase = true)
            }

            items(filteredList) { record ->
                var status by remember(record.id) { mutableStateOf("P") }
                AttendanceStudentCard(
                    name = "${record.students.last_name}, ${record.students.first_name}",
                    studentId = record.students.student_id_number,
                    imageRes = R.drawable.boy,
                    selectedStatus = status,
                    onStatusChange = { status = it }
                )
            }
        }
    }
}