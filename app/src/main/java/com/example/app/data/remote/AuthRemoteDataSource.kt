package com.example.app.data.remote

import com.example.app.domain.model.RegisterRequest
import com.example.app.domain.model.RegisterResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.exception.AuthRestException
import java.io.IOException

// Класс для отправки запросов на сервер
class AuthRemoteDataSource(
    // supabaseClient - это подключение к серверу Supabase
    private val supabaseClient: SupabaseClient
) {

    // Функция регистрации
    suspend fun register(request: RegisterRequest): Result<RegisterResponse> {
        return try {
            // Пытаемся зарегистрироваться на сервере
            val result = supabaseClient.auth.signUpWith(
                email = request.email,
                password = request.password
            )

            // Если успешно - возвращаем успех с ID пользователя
            Result.success(
                RegisterResponse(
                    id = result.user?.id,
                    error = null
                )
            )
        } catch (e: AuthRestException) {
            // Если сервер вернул ошибку (например, email уже есть)
            Result.failure(Exception(e.errorDescription ?: "Ошибка регистрации"))
        } catch (e: IOException) {
            // Если нет интернета
            Result.failure(Exception("Отсутствует подключение к интернету"))
        } catch (e: Exception) {
            // Другие ошибки
            Result.failure(Exception("Произошла ошибка: ${e.message}"))
        }
    }
}