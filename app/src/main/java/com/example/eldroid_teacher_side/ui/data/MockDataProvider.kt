package com.example.eldroid_teacher_side.ui.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

object MockDataProvider {
    @RequiresApi(Build.VERSION_CODES.O)
    val eventDates = listOf(
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(3),
        LocalDate.now().minusDays(2),
        LocalDate.now().plusWeeks(1).with(java.time.DayOfWeek.TUESDAY),
        LocalDate.now().plusWeeks(1).with(java.time.DayOfWeek.THURSDAY)
    )

    data class ScheduleItem(
        val code: String,
        val name: String,
        val time: String,
        val room: String,
        val isActive: Boolean = false,
        val date: LocalDate
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val schedules = listOf(
        ScheduleItem("CS202", "Data Structures & Algorithms", "01:00 PM - 02:30 PM", "Room 402", true, LocalDate.now()),
        ScheduleItem("IT101", "Introduction to Computing", "02:45 PM - 04:15 PM", "Lab B", false, LocalDate.now()),
        ScheduleItem("MATH11", "Discrete Mathematics", "09:00 AM - 10:30 AM", "Room 101", false, LocalDate.now().plusDays(1)),
        ScheduleItem("GE105", "Ethics", "10:45 AM - 12:15 PM", "Room 205", false, LocalDate.now().plusDays(1))
    )
}
