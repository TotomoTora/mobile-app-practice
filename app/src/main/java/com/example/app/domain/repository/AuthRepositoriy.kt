package com.example.app.domain.repository

import com.example.app.domain.model.RegisterRequest
import com.example.app.domain.model.RegisterResponse

// interface - это описание того, что должен уметь делать класс
interface AuthRepository {
    // suspend - функция, которая работает с интернетом (асинхронная)
    // Result - результат операции (успех или ошибка)
    suspend fun register(request: RegisterRequest): Result<RegisterResponse>
}