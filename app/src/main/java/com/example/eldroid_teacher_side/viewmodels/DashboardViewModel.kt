package com.example.eldroid_teacher_side.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.ui.data.Course
import com.example.eldroid_teacher_side.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    init {
        // This is where the call is triggered as soon as the screen opens!
        fetchCourses()
    }

    private fun fetchCourses() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getCourses()
                if (response.status == "success") {
                    _courses.value = response.data
                }
            } catch (e: Exception) {
                println("Network Error: ${e.message}")
            }
        }
    }
}