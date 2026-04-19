package com.example.eldroid_teacher_side.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.data.CalendarDayData
import com.example.eldroid_teacher_side.util.generateCurrentWeek
import com.example.eldroid_teacher_side.util.generateMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyCalendarCard(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    eventDates: List<LocalDate> = emptyList()
) {
    var isExpanded by remember { mutableStateOf(false) }
    var currentMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }
    var currentWeekDate by remember { mutableStateOf(selectedDate) }

    val weekDays = remember(currentWeekDate) { generateCurrentWeek(currentWeekDate) }
    val monthDays = remember(currentMonth) { generateMonth(currentMonth) }

    val displayMonth = if (isExpanded) {
        currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    } else {
        selectedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Month Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (isExpanded) currentMonth = currentMonth.minusMonths(1)
                    else currentWeekDate = currentWeekDate.minusWeeks(1)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.k_arrow_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(23.dp)
                    )
                }

                Text(
                    text = displayMonth,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )

                IconButton(onClick = {
                    if (isExpanded) currentMonth = currentMonth.plusMonths(1)
                    else currentWeekDate = currentWeekDate.plusWeeks(1)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.k_arrow_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(23.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (!isExpanded) {
                // WEEKLY VIEW
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    weekDays.forEach { day ->
                        CalendarDayItem(
                            day = day.copy(hasEvent = eventDates.any { it.isEqual(day.fullDate) }),
                            isSelected = day.fullDate.isEqual(selectedDate),
                            onDateSelected = onDateSelected,
                            showDayName = true
                        )
                    }
                }
            } else {
                // EXPANDED MONTHLY VIEW
                Column {
                    // Header Row for Days (Sundays in Red)
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { index, day ->
                            Text(
                                text = day,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (index == 6) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))

                    // Fixed clipping: Using heightIn to allow dynamic expansion for the 6th row
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier.heightIn(max = 500.dp),
                        userScrollEnabled = false,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(monthDays) { day ->
                            val isCurrentMonth = YearMonth.from(day.fullDate) == currentMonth
                            CalendarDayItem(
                                day = day.copy(hasEvent = eventDates.any { it.isEqual(day.fullDate) }),
                                isSelected = day.fullDate.isEqual(selectedDate),
                                onDateSelected = onDateSelected,
                                isDimmed = !isCurrentMonth,
                                showDayName = false // Prevent name repetition
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDayItem(
    day: CalendarDayData,
    isSelected: Boolean,
    onDateSelected: (LocalDate) -> Unit,
    isDimmed: Boolean = false,
    showDayName: Boolean = true
) {
    val isSunday = day.fullDate.dayOfWeek == DayOfWeek.SUNDAY

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onDateSelected(day.fullDate) }
            .padding(vertical = 4.dp)
    ) {
        if (showDayName && !isDimmed) {
            Text(
                text = day.dayName.take(3).uppercase(),
                color = if (isSunday) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp,
                fontWeight = if (isSunday) FontWeight.Bold else FontWeight.Normal
            )
            Spacer(Modifier.height(4.dp))
        }

        Surface(
            shape = CircleShape,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
            modifier = Modifier.size(32.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = day.dayNumber,
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        isDimmed -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        isSunday -> Color.Red
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontWeight = if (isSelected || isSunday) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }

        if (day.hasEvent) {
            Box(
                Modifier
                    .padding(top = 2.dp)
                    .size(4.dp)
                    .background(
                        if (isSunday && !isSelected) Color.Red else MaterialTheme.colorScheme.tertiary,
                        CircleShape
                    )
            )
        } else {
            // Keep spacing consistent
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
fun ScheduleCard(
    code: String,
    name: String,
    time: String,
    room: String,
    isActive: Boolean = false
){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)){
            if(isActive){
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.primary)
                )
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = code,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
                Text(text = name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Senior Fix: Stacked layout for Time and Location to handle long names
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(painter = painterResource(R.drawable.clock), contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = " $time ", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Icon(imageVector = Icons.Outlined.Place, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = " $room ", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier.padding(16.dp).size(20.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
            )
        }
    }
}