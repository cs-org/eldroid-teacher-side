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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.ScheduleCard
import com.example.eldroid_teacher_side.ui.components.WeeklyCalendarCard
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.data.MockDataProvider
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(navController: NavController){

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    
    // Use static event dates from MockDataProvider
    val eventDates = MockDataProvider.eventDates

    // Filter schedules based on the selected date
    val filteredSchedules = remember(selectedDate) {
        MockDataProvider.schedules.filter { it.date.isEqual(selectedDate) }
    }

    val currentDayText = selectedDate.dayOfWeek.name
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
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            WeeklyCalendarCard(
                selectedDate = selectedDate,
                eventDates = eventDates,
                onDateSelected = { selectedDate = it }
            )


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
                           text = "$currentDayText Classes",
                           fontSize = 20.sp,
                           fontWeight = FontWeight.Bold,
                           color = Color(0xFF1B3D2F),
                           modifier = Modifier.padding(bottom = 8.dp)
                       )

                       Text (
                           text = "${filteredSchedules.size} Sessions",
                           fontSize = 12.sp,
                           fontWeight = FontWeight.Bold,
                           color = Color.Gray
                       )
                   }
                }

                if (filteredSchedules.isEmpty()) {
                    item {
                        Text(
                            text = "No classes scheduled for this day.",
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                } else {
                    items(filteredSchedules) { schedule ->
                        ScheduleCard(
                            code = schedule.code,
                            name = schedule.name,
                            time = schedule.time,
                            room = schedule.room,
                            isActive = schedule.isActive
                        )
                    }
                }
            }
        }

    }
}
