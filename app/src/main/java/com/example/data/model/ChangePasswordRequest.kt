package com.example.data.model

data class ChangePasswordRequest(
    val email: String,
    val newPassword: String
)
