package com.example.eldroid_teacher_side.ui.data

data class CoursesResponse(
    val status: String,
    val data: List<Course>
)

data class Course(
    val id: Int,
    val course_code: String,
    val course_name: String,
    val room_assignment: String,
    val schedule_days: String,
    val schedule_time: String
)

data class LoginRequest(
    val facultyId: String,
    val password: String
)

data class LoginResponse(
    val status: String,
    val message: String,
    val faculty_data: FacultyData? = null
)

data class FacultyData(
    val facultyId: String,
    val fullName: String,
    val email: String,
    val phone: String,
    val profileImage: String?
)

data class StudentGradesResponse(
    val status: String,
    val data: List<StudentGrade>
)

data class StudentGrade(
    val id: Int,
    val course_id: Int,
    val midterm_grade: Double?, // Nullable because they might not be graded yet
    val finals_grade: Double?,
    val current_grade: Double?,
    val status: String?,
    val students: StudentInfo
)

data class StudentInfo(
    val student_id_number: String,
    val first_name: String,
    val last_name: String
)

data class MessagesResponse(
    val status: String,
    val data: List<ParentMessage>
)

data class ParentMessage(
    val id: Int,
    val sender_name: String,
    val student_relation: String,
    val message_preview: String,
    val received_time: String,
    val unread_count: Int
)

// Matches the 'faculty_credentials' table
data class FacultyCredential(
    val id: Int,
    val degree_title: String,
    val institution: String,
    val year_obtained: String,
    val type: String // 'degree' or 'certification'
)

// The full response including the mission from the 'faculty' table
data class CredentialsResponse(
    val status: String,
    val data: List<FacultyCredential>,
    val mission: String?
)