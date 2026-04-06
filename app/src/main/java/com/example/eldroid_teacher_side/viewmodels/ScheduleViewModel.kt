package com.example.eldroid_teacher_side.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.ui.data.Course
import com.example.eldroid_teacher_side.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

class ScheduleViewModel : ViewModel() {

    private val _dailySchedule = MutableStateFlow<List<Course>>(emptyList())
    val dailySchedule: StateFlow<List<Course>> = _dailySchedule

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun fetchScheduleForDate(date: LocalDate) {
        val dayName = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getSchedule(dayName)
                if (response.status == "success") {
                    _dailySchedule.value = response.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}