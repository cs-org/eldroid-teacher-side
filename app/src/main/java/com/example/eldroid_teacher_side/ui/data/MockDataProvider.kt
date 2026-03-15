package com.example.eldroid_teacher_side.ui.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.eldroid_teacher_side.R
import java.time.LocalDate

data class StudentMock(
    val name: String,
    val id: String,
    val midterm: String,
    val finals: String,
    val status: String,
    val imageRes: Int = R.drawable.boy
)

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
        val days: String = "MWF",
        val isActive: Boolean = false,
        val date: LocalDate
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val schedules = listOf(
        ScheduleItem("CS101", "Computer Science I", "09:00 AM - 10:30 AM", "Room 402 • Engineering Bldg", "TTH", true, LocalDate.now()),
        ScheduleItem("CS202", "Data Structures & Algorithms", "01:00 PM - 02:30 PM", "Lab 3 • IT Building", "MWF", false, LocalDate.now()),
        ScheduleItem("IT101", "Introduction to Computing", "02:45 PM - 04:15 PM", "Lab B", "MWF", false, LocalDate.now()),
        ScheduleItem("MATH11", "Discrete Mathematics", "09:00 AM - 10:30 AM", "Room 101", "TTH", false, LocalDate.now()),
        ScheduleItem("GE105", "Ethics", "10:45 AM - 12:15 PM", "Room 205", "TTH", false, LocalDate.now())
    )

    val faqs = listOf(
        FAQItem(
            question = "How do I reset my portal password?",
            answer = "You can reset your password by clicking 'Forgot Password' on the login screen or by visiting the IT Department with your Faculty ID."
        ),
        FAQItem(
            question = "How do I submit student grades?",
            answer = "Grades can be submitted through the 'Grading' section in your dashboard. Ensure you follow the deadline set by the Registrar's Office."
        ),
        FAQItem(
            question = "Where can I see my teaching schedule?",
            answer = "Your teaching schedule is available on the Home screen under the 'Today's Schedule' section and in the 'Academic' tab."
        ),
        FAQItem(
            question = "How do I request a leave of absence?",
            answer = "Leave requests should be filed through the HR portal at least 3 days in advance for planned leaves."
        )
    )

    val courseStudents = mapOf(
        "CS101 - Computer Science I" to listOf(
            StudentMock("Abad, Julianne Marie", "2021-00124", "92", "", "ON TRACK"),
            StudentMock("Bautista, Lorenzo", "2021-00156", "78", "", "NEEDS REVIEW"),
            StudentMock("Chen, Samuel", "2023CS092", "85", "88", "ON TRACK")
        ),
        "CS202 - Data Structures" to listOf(
            StudentMock("Dela Cruz, Juan", "2021-00201", "88", "90", "ON TRACK"),
            StudentMock("Estacio, Elena", "2021-00202", "72", "75", "NEEDS REVIEW"),
            StudentMock("Fernandez, Miguel", "2021-00203", "95", "96", "ON TRACK")
        ),
        "IT101 - Introduction to Computing" to listOf(
            StudentMock("Gomez, Roberto", "2021-00301", "80", "", "ON TRACK"),
            StudentMock("Hidalgo, Sofia", "2021-00302", "65", "68", "NEEDS REVIEW"),
            StudentMock("Ibarra, Kristine", "2021-00303", "88", "89", "ON TRACK")
        ),
        "MATH11 - Discrete Mathematics" to listOf(
            StudentMock("Javier, Paolo", "2021-00401", "90", "92", "ON TRACK"),
            StudentMock("Kapunan, Katrina", "2021-00402", "82", "85", "ON TRACK"),
            StudentMock("Laxamana, Lean", "2021-00403", "70", "72", "NEEDS REVIEW")
        )
    )
}
