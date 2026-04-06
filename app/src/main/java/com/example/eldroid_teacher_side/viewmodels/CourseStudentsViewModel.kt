package com.example.eldroid_teacher_side.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.ui.data.Course
import com.example.eldroid_teacher_side.ui.data.StudentGrade
import com.example.eldroid_teacher_side.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CourseStudentsViewModel : ViewModel() {

    // The list of courses for the Dropdown
    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    // The currently selected course
    private val _selectedCourse = MutableStateFlow<Course?>(null)
    val selectedCourse: StateFlow<Course?> = _selectedCourse

    // The students for the selected course
    private val _studentGrades = MutableStateFlow<List<StudentGrade>>(emptyList())
    val studentGrades: StateFlow<List<StudentGrade>> = _studentGrades

    init {
        fetchCourses()
    }

    private fun fetchCourses() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getCourses()
                if (response.status == "success" && response.data.isNotEmpty()) {
                    _courses.value = response.data
                    // Automatically select the first course and load its students!
                    selectCourse(response.data.first())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun selectCourse(course: Course) {
        _selectedCourse.value = course
        fetchGradesForCourse(course.id)
    }

    private fun fetchGradesForCourse(courseId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getCourseStudents(courseId)
                if (response.status == "success") {
                    _studentGrades.value = response.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Dynamic calculations for your Summary Cards!
    fun getClassAverage(): String {
        val grades = _studentGrades.value.mapNotNull { it.current_grade }
        if (grades.isEmpty()) return "0.0%"
        val avg = grades.average()
        return String.format("%.1f%%", avg)
    }

    fun getPendingCount(): String {
        // Count students who don't have a finals grade yet
        return _studentGrades.value.count { it.finals_grade == null }.toString()
    }
}