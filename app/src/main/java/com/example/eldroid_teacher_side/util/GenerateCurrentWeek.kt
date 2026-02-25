package com.example.eldroid_teacher_side.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.eldroid_teacher_side.ui.data.CalendarDayData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjuster
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
fun generateCurrentWeek(): List<CalendarDayData> {
    val now = LocalDate.now()

    val monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    return (0..4).map { i ->
        val date = monday.plusDays(i.toLong())
        CalendarDayData(
            dayName = date.dayOfWeek.name.take(3),
            dayNumber = date.dayOfMonth.toString().padStart(2, '0'),
            fullDate = date
        )
    }
}

