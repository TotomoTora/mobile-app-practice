package com.example.app.data.repository

import com.example.app.data.remote.AuthRemoteDataSource
import com.example.app.domain.model.RegisterRequest
import com.example.app.domain.model.RegisterResponse
import com.example.app.domain.repository.AuthRepository

// Реализация интерфейса AuthRepository
class AuthRepositoryImpl(
    // remoteDataSource - для отправки запросов на сервер
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    // Функция регистрации (обязаны реализовать, потому что implements AuthRepository)
    override suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        // Просто передаем запрос в remoteDataSource
        return remoteDataSource.register(request)
    }
}