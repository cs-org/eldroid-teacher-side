package com.example.eldroid_teacher_side.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.ui.components.AttendanceSearchBar
import com.example.eldroid_teacher_side.ui.components.AttendanceStudentCard
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.AttendanceCalendarHeader
import com.example.eldroid_teacher_side.ui.components.BaseScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen() {

    var searchQuery by remember { mutableStateOf("") }
    val students = listOf("Chen, Samuel", "Garcia, Maria", "Reyes, Juan")

    BaseScreen(
        title = "Attendance",
        subtitle = "FACULTY PORTAL",
        navigationIcon = {
            Icon(
                painter = painterResource(R.drawable.arrow_left),
                contentDescription = "Back",
                modifier = Modifier.size(24.dp).clickable { /* Handle back */ }
            )
        },
        actions = {
            Surface(
                color = Color(0xFF1B3D2F),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, Color(0xFF1B3D2F).copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.graduate),
                        contentDescription = null,
                        tint = Color(0xFFC5A347),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "CS202",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp

            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1B3D2F)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Submit Attendance Report",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            AttendanceCalendarHeader()
            AttendanceSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it}
            )

            Text(
                text = "STUDENT LIST (${students.size})",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            LazyColumn() {
                items(students) { student ->
                    var status by remember { mutableStateOf("P") }

                    AttendanceStudentCard(
                        name = student,
                        studentId = "2023CS092",
                        imageRes = R.drawable.boy, // Use your drawable
                        selectedStatus = status,
                        onStatusChange = { status = it }
                    )
                }
            }
        }
    }
}