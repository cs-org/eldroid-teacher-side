package com.example.eldroid_teacher_side.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserProfile(
    val fullName: String,
    val facultyId: String,
    val email: String,
    val profileImage: String, // Add this for the URL
    val role: String = "Senior Lecturer"
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPrefs = application.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _userProfile = MutableStateFlow(loadProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    private fun loadProfile(): UserProfile {
        return UserProfile(
            fullName = sharedPrefs.getString("full_name", "Prof. User") ?: "Prof. User",
            facultyId = sharedPrefs.getString("faculty_id", "0000-00000") ?: "0000-00000",
            email = sharedPrefs.getString("email", "user@colegio.edu") ?: "",
            // Get the URL we saved during login.
            // We provide an empty string as default so Coil can handle it or show a placeholder.
            profileImage = sharedPrefs.getString("profile_image", "") ?: ""
        )
    }

    // Call this if data changes (e.g., after an update)
    fun refresh() {
        _userProfile.value = loadProfile()
    }
}