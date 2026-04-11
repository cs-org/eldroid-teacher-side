package com.example.eldroid_teacher_side.network

import com.example.eldroid_teacher_side.ui.data.CoursesResponse
import com.example.eldroid_teacher_side.ui.data.CredentialsResponse
import com.example.eldroid_teacher_side.ui.data.LoginRequest
import com.example.eldroid_teacher_side.ui.data.LoginResponse
import com.example.eldroid_teacher_side.ui.data.MessagesResponse
import com.example.eldroid_teacher_side.ui.data.StudentGradesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

// 1. Define the Routes
interface FacultyApiService {
    @GET("api/courses")
    suspend fun getCourses(): CoursesResponse

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/courses/{course_id}/students")
    suspend fun getCourseStudents(@Path("course_id") courseId: Int): StudentGradesResponse

    @GET("api/schedule/{day}")
    suspend fun getSchedule(@Path("day") day: String): CoursesResponse

    @GET("api/messages")
    suspend fun getMessages(): MessagesResponse

    @GET("api/faculty/{fid}/credentials")
    suspend fun getCredentials(@Path("fid") facultyId: String): CredentialsResponse
}

// 2. Build the Client
object RetrofitClient {
    // 10.0.2.2 is the emulator's way to access your computer's localhost
    private const val BASE_URL = "https://eldroid-backend.vercel.app/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: FacultyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FacultyApiService::class.java)
    }
}