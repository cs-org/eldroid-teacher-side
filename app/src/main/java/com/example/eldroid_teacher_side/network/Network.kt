package com.example.eldroid_teacher_side.network

import android.content.Context
import com.example.eldroid_teacher_side.ui.data.ChatHistoryResponse
import com.example.eldroid_teacher_side.ui.data.CoursesResponse
import com.example.eldroid_teacher_side.ui.data.CredentialsResponse
import com.example.eldroid_teacher_side.ui.data.LoginRequest
import com.example.eldroid_teacher_side.ui.data.LoginResponse
import com.example.eldroid_teacher_side.ui.data.MessageData
import com.example.eldroid_teacher_side.ui.data.MessageResponseWrapper
import com.example.eldroid_teacher_side.ui.data.MessagesResponse
import com.example.eldroid_teacher_side.ui.data.StudentGradesResponse
import okhttp3.Interceptor
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
    suspend fun getMessages(): MessageResponseWrapper

    @GET("api/faculty/{fid}/credentials")
    suspend fun getCredentials(@Path("fid") facultyId: String): CredentialsResponse

    @GET("api/chat/history/{receiver_id}")
    suspend fun getChatHistory(
        @Path("receiver_id") receiverId: String
    ): ChatHistoryResponse // Change this from List to the Wrapper class
}

object RetrofitClient {
    private const val BASE_URL = "https://eldroid-backend-express.onrender.com/"

    // We will initialize this from MainActivity or Application class
    private var tokenManager: TokenManager? = null

    fun init(context: Context) {
        tokenManager = TokenManager(context)
    }

    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()

        tokenManager?.getToken()?.let {
            // Use .header to ensure only one Authorization header exists
            requestBuilder.header("Authorization", "Bearer $it")
        }

        chain.proceed(requestBuilder.build())
    }
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(authInterceptor) // <--- Crucial step
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

object ChatSocketHandler {
    private lateinit var mSocket: io.socket.client.Socket
    private const val SOCKET_URL = "https://eldroid-backend-express.onrender.com/"

    fun init(token: String) {
        try {
            val options = io.socket.client.IO.Options().apply {
                // Pass the same Bearer token your RetrofitClient uses
                auth = mapOf("token" to token)
                transports = arrayOf("websocket")
            }
            mSocket = io.socket.client.IO.socket(SOCKET_URL, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun connect() = mSocket.connect()
    fun disconnect() = mSocket.disconnect()
    fun getSocket() = mSocket
}