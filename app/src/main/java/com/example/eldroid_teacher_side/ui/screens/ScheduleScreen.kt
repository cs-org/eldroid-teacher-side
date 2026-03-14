package com.example.eldroid_teacher_side.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.ScheduleCard
import com.example.eldroid_teacher_side.ui.components.WeeklyCalendarCard
import com.example.eldroid_teacher_side.ui.data.MockDataProvider
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val eventDates = MockDataProvider.eventDates
    val filteredSchedules = remember(selectedDate) {
        MockDataProvider.schedules.filter { it.date.isEqual(selectedDate) }
    }

    val currentDayText = selectedDate.dayOfWeek.name
        .lowercase()
        .replaceFirstChar { it.uppercaseChar() }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header without back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Faculty Schedule",
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
                ) {
                    Text(
                        text = "$currentDayText Classes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "${filteredSchedules.size} Sessions",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (filteredSchedules.isEmpty()) {
                item {
                    Text(
                        text = "No classes scheduled for this day.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
