package com.example.app.domain.model

data class RegisterResponse(
    val id: String? = null,     // ID пользователя (если успешно)
    val error: String? = null   // Текст ошибки (если не успешно)
)
