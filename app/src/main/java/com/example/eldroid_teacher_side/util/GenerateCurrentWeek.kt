package com.example.eldroid_teacher_side.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.eldroid_teacher_side.ui.data.CalendarDayData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
fun generateCurrentWeek(date: LocalDate = LocalDate.now()): List<CalendarDayData> {
    val monday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    return (0..6).map { i ->
        val currentDate = monday.plusDays(i.toLong())
        CalendarDayData(
            dayName = currentDate.dayOfWeek.name.take(3),
            dayNumber = currentDate.dayOfMonth.toString().padStart(2, '0'),
            fullDate = currentDate,
            hasEvent = false // This can be wired to a real event source later
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun generateMonth(yearMonth: YearMonth): List<CalendarDayData> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val firstVisibleDay = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val lastVisibleDay = lastDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

    val days = mutableListOf<CalendarDayData>()
    var curr = firstVisibleDay
    while (!curr.isAfter(lastVisibleDay)) {
        days.add(
            CalendarDayData(
                dayName = curr.dayOfWeek.name.take(3),
                dayNumber = curr.dayOfMonth.toString().padStart(2, '0'),
                fullDate = curr,
                hasEvent = false // Mock event data
            )
        )
        curr = curr.plusDays(1)
    }
    return days
}
