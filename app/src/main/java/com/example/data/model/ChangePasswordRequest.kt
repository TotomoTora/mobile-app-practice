package com.example.practice.data.model

data class ChangePasswordRequest(
    val email: String,
    val newPassword: String
)
