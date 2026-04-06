package com.example.eldroid_teacher_side.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eldroid_teacher_side.ui.data.LoginRequest
import com.example.eldroid_teacher_side.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    // This holds the actual user data from the database
    data class Success(val data: com.example.eldroid_teacher_side.ui.data.FacultyData) : LoginState()
    data class Error(val message: String) : LoginState()
}

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(facultyId: String, password: String) {
        if (facultyId.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Please enter both ID and password.")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val request = LoginRequest(facultyId, password)
                val response = RetrofitClient.apiService.login(request)

                // FIX: Pass the faculty_data into the Success state
                if (response.status == "success" && response.faculty_data != null) {
                    _loginState.value = LoginState.Success(data = response.faculty_data)
                } else {
                    _loginState.value = LoginState.Error("Unexpected response from server.")
                }

            } catch (e: HttpException) {
                when (e.code()) {
                    404 -> _loginState.value = LoginState.Error("Wrong username / Faculty ID not found.")
                    401 -> _loginState.value = LoginState.Error("Incorrect password.")
                    else -> _loginState.value = LoginState.Error("Server error: ${e.code()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Network Error. Is the server running?")
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}