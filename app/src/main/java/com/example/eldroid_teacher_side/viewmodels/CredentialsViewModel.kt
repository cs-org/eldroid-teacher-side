package com.example.eldroid_teacher_side.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.network.RetrofitClient
import com.example.eldroid_teacher_side.ui.data.FacultyCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CredentialsViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // This looks for the key we saved in MainActivity during login
    private val facultyId = prefs.getString("faculty_id", "") ?: ""

    private val _credentials = MutableStateFlow<List<FacultyCredential>>(emptyList())
    val credentials: StateFlow<List<FacultyCredential>> = _credentials

    private val _mission = MutableStateFlow("Loading academic mission...")
    val mission: StateFlow<String> = _mission

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        if (facultyId.isNotEmpty()) {
            fetchFacultyRecords()
        }
    }

    fun fetchFacultyRecords() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Calls the route we just added to your Network.kt
                val response = RetrofitClient.apiService.getCredentials(facultyId)

                if (response.status == "success") {
                    _credentials.value = response.data
                    _mission.value = response.mission ?: "No academic mission set."
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _mission.value = "Failed to load records from server."
            } finally {
                _isLoading.value = false
            }
        }
    }
}