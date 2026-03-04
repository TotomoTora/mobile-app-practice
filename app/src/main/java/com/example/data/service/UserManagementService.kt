package com.example.practice.data.service

import com.example.data.model.SignUpResponse
import com.example.data.model.VerifyOtpRequest
import com.example.data.model.VerifyOtpResponse
import com.example.data.model.*
import retrofit2.Response
import retrofit2.http.*

const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdrZHlva29ncWxjZHFseHd1b3pvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzI1MjY4MTEsImV4cCI6MjA4ODEwMjgxMX0.wWRgbr1ScucUI7BOzXwO3VYjNXTy-6BWCsL9Dmd4kE0"

data class ProfileDto(
    val id: String?,
    val user_id: String?,
    val photo: String?,
    val firstname: String?,
    val lastname: String?,
    val address: String?,
    val phone: String?
)

interface UserManagementService {

    // ---------- РЕГИСТРАЦИЯ ----------
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/signup")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    // ---------- ВХОД ----------
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    // ---------- ВОССТАНОВЛЕНИЕ ПАРОЛЯ ----------
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/recover")
    suspend fun recoverPassword(@Body body: Map<String, String>): Response<Any>

    // ---------- ПРОВЕРКА OTP ----------
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @POST("auth/v1/verify")
    suspend fun verifyOTP(@Body request: VerifyOtpRequest): Response<VerifyOtpResponse>


    @Headers("apikey: ${com.example.practice.data.service.API_KEY}", "Content-Type: application/json")
    @POST("change-password")
    suspend fun changePassword(@Body body: ChangePasswordRequest): Response<Any>


    @Headers("apikey: ${com.example.practice.data.service.API_KEY}")
    @GET("rest/v1/profiles")
    suspend fun getProfile(
        @Header("Authorization") authHeader: String,
        @Query("user_id") userIdFilter: String, // "eq.<uuid>"
        @Query("select") select: String = "*"
    ): List<com.example.practice.data.service.ProfileDto>

    @Headers("apikey: ${com.example.practice.data.service.API_KEY}", "Content-Type: application/json")
    @PUT("rest/v1/profiles")
    suspend fun updateProfile(
        @Header("Authorization") authHeader: String,
        @Query("user_id") userIdFilter: String,
        @Body body: Map<String, Any?>
    ): Response<Unit>

    // ---------- ИЗМЕНЕНИЕ ПАРОЛЯ ----------
    @Headers("apikey: $API_KEY", "Content-Type: application/json")
    @PUT("auth/v1/user")
    suspend fun changePassword(
        @Header("Authorization") authHeader: String,
        @Body request: ChangePasswordRequest
    ): Response<Any>
}