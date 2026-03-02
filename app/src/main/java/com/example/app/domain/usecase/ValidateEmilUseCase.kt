package com.example.app.domain.usecase

class ValidateEmailUseCase {

    // Это правило (регулярное выражение) для проверки email
    // Оно означает: имя@домен.домен (только маленькие буквы и цифры)
    private val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$")

    // Эта функция будет вызываться для проверки email
    // operator fun invoke - специальная функция, чтобы можно было писать: ValidateEmailUseCase(email)
    operator fun invoke(email: String): Boolean {
        // matches проверяет, подходит ли email под правило
        return emailPattern.matches(email)
    }
}
