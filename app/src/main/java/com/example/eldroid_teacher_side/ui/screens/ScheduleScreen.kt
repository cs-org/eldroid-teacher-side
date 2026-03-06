package com.example.eldroid_teacher_side.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.ScheduleCard
import com.example.eldroid_teacher_side.ui.components.WeeklyCalendarCard
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.BottomBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(navController: NavController){

    val currentDay = java.time.LocalDate.now().dayOfWeek.name
        .lowercase()
        .replaceFirstChar { it.uppercaseChar() }

    BaseScreen(
        title = "Faculty Schedule",
        subtitle = "Faculty Portal",
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = Color(0xFF004020),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        bottomBar = {
            BottomBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            WeeklyCalendarCard()


            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                   Row(
                       modifier = Modifier.fillMaxWidth(),
                       horizontalArrangement = Arrangement.SpaceBetween,
                       verticalAlignment = Alignment.CenterVertically
                   ){
                       Text(
                           text = "$currentDay Classes",
                           fontSize = 20.sp,
                           fontWeight = FontWeight.Bold,
                           color = Color(0xFF1B3D2F),
                           modifier = Modifier.padding(bottom = 8.dp)
                       )

                       Text (
                           text = "2 Sessions",
                           fontSize = 12.sp,
                           fontWeight = FontWeight.Bold,
                           color = Color.Gray
                       )
                   }
                }

                item {
                    ScheduleCard("CS202", "Data Structures & Algorithms", "01:00 PM - 02:30 PM", "Room 402", true)
                }
                item {
                    ScheduleCard("IT101", "Introduction to Computing", "02:45 PM - 04:15 PM", "Lab B")
                }
            }
        }

    }
}
