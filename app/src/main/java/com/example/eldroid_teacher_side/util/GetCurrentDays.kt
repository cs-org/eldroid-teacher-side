package com.example.eldroid_teacher_side.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

data class DateModel(
    val dayName: String,
    val dayNumber: String,
    val isToday: Boolean
)

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentWeekDays(): List<DateModel> {
    val today = LocalDate.now()

    val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    return (0..4).map { i ->
        val date = monday.plusDays(i.toLong())
        DateModel(
            dayName = date.dayOfWeek.name.take(3), // e.g., "MON"
            dayNumber = date.dayOfMonth.toString(),
            isToday = date == today
        )
    }
}

