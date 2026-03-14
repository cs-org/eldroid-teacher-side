package com.example.eldroid_teacher_side.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.data.CalendarDayData
import com.example.eldroid_teacher_side.util.generateCurrentWeek
import com.example.eldroid_teacher_side.util.generateMonth
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (isExpanded) {
                        currentMonth = currentMonth.minusMonths(1)
                    } else {
                        currentWeekDate = currentWeekDate.minusWeeks(1)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.k_arrow_left),
                        contentDescription = "Previous",
                        modifier = Modifier.size(23.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = displayMonth,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { isExpanded = !isExpanded }
                )

                IconButton(onClick = {
                    if (isExpanded) {
                        currentMonth = currentMonth.plusMonths(1)
                    } else {
                        currentWeekDate = currentWeekDate.plusWeeks(1)
                    }
                }) {
                    Icon(
                        painter = painterResource(R.drawable.k_arrow_right),
                        contentDescription = "Next",
                        modifier = Modifier.size(23.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            if (!isExpanded) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    weekDays.forEach { day ->
                        CalendarDayItem(
                            day = day.copy(hasEvent = eventDates.any { it.isEqual(day.fullDate) }),
                            isSelected = day.fullDate.isEqual(selectedDate),
                            onDateSelected = onDateSelected
                        )
                    }
                }
            } else {
                Column {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN").forEach {
                            Text(
                                text = it,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    
                    Box(modifier = Modifier.height(240.dp)) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            userScrollEnabled = false,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(monthDays) { day ->
                                val isCurrentMonth = YearMonth.from(day.fullDate) == currentMonth
                                CalendarDayItem(
                                    day = day.copy(hasEvent = eventDates.any { it.isEqual(day.fullDate) }),
                                    isSelected = day.fullDate.isEqual(selectedDate),
                                    onDateSelected = onDateSelected,
                                    isDimmed = !isCurrentMonth
                                )
                            }
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
    isDimmed: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onDateSelected(day.fullDate) }
            .padding(vertical = 4.dp)
    ) {
        if (!isDimmed) {
            Text(
                text = day.dayName,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 10.sp
            )
        }
        Spacer(Modifier.height(4.dp))
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
                        isDimmed -> MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f)
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }

        if (day.hasEvent) {
            Box(
                Modifier
                    .padding(top = 2.dp)
                    .size(4.dp)
                    .background(MaterialTheme.colorScheme.tertiary, CircleShape)
            )
        } else {
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

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.clock),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " $time ",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Icon(
                        imageVector = Icons.Outlined.Place,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = " $room ",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
