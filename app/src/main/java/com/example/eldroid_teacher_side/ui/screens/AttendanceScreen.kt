package com.example.eldroid_teacher_side.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val students = listOf("Chen, Samuel", "Garcia, Maria", "Reyes, Juan")

    Column(modifier = Modifier.fillMaxSize()) {
        // Header without back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
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
                    Text(
                        text = "CS202",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        AttendanceCalendarHeader()
        AttendanceSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )

        Text(
            text = "STUDENT LIST (${students.size})",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(students) { student ->
                var status by remember { mutableStateOf("P") }
                AttendanceStudentCard(
                    name = student,
                    studentId = "2023CS092",
                    imageRes = R.drawable.boy,
                    selectedStatus = status,
                    onStatusChange = { status = it }
                )
            }
        }
    }
}
