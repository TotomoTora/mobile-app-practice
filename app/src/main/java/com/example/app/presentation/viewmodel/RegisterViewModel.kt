package com.example.app.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.domain.model.RegisterRequest
import com.example.app.domain.repository.AuthRepository
import com.example.app.domain.usecase.ValidateEmailUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,      // для регистрации
    private val validateEmailUseCase: ValidateEmailUseCase  // для проверки email
) : ViewModel() {

    // === Состояния экрана ===

    // Видимость пароля (true - показывать, false - скрывать)
    private val _isPasswordVisible = MutableLiveData(false)
    val isPasswordVisible: LiveData<Boolean> = _isPasswordVisible

    // Видимость подтверждения пароля
    private val _isConfirmPasswordVisible = MutableLiveData(false)
    val isConfirmPasswordVisible: LiveData<Boolean> = _isConfirmPasswordVisible

    // Приняты ли условия
    private val _isTermsAccepted = MutableLiveData(false)
    val isTermsAccepted: LiveData<Boolean> = _isTermsAccepted

    // Активна ли кнопка регистрации
    private val _isRegisterButtonEnabled = MutableLiveData(false)
    val isRegisterButtonEnabled: LiveData<Boolean> = _isRegisterButtonEnabled

    // Текст ошибки
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Идет ли загрузка
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Успешна ли регистрация
    private val _isRegistrationSuccess = MutableLiveData(false)
    val isRegistrationSuccess: LiveData<Boolean> = _isRegistrationSuccess

    // Данные из полей ввода
    var email = ""
    var password = ""
    var confirmPassword = ""

    // === Функции для изменения состояний ===

    // Переключить видимость пароля
    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !(_isPasswordVisible.value ?: false)
    }

    // Переключить видимость подтверждения пароля
    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !(_isConfirmPasswordVisible.value ?: false)
    }

    // Установить, приняты ли условия
    fun setTermsAccepted(accepted: Boolean) {
        _isTermsAccepted.value = accepted
        updateRegisterButtonState()
    }

    // Обновить состояние кнопки (активна/неактивна)
    private fun updateRegisterButtonState() {
        val isEnabled = (_isTermsAccepted.value == true) &&
                email.isNotBlank() &&
                password.isNotBlank() &&
                confirmPassword.isNotBlank()
        _isRegisterButtonEnabled.value = isEnabled
    }

    // Проверить, правильно ли заполнена форма
    fun validateForm(): Boolean {
        _errorMessage.value = null

        // Проверка email
        if (!validateEmailUseCase(email)) {
            _errorMessage.value = "Некорректный email. Используйте формат: имя@домен.ru (только маленькие буквы и цифры)"
            return false
        }

        // Проверка пароля
        if (password.isEmpty()) {
            _errorMessage.value = "Введите пароль"
            return false
        }

        if (password.length < 6) {
            _errorMessage.value = "Пароль должен содержать минимум 6 символов"
            return false
        }

        // Проверка совпадения паролей
        if (password != confirmPassword) {
            _errorMessage.value = "Пароли не совпадают"
            return false
        }

        return true
    }

    // Зарегистрироваться
    fun register() {
        // Сначала проверяем форму
        if (!validateForm()) {
            return
        }

        // Запускаем регистрацию в фоне
        viewModelScope.launch {
            _isLoading.value = true  // показать загрузку

            val request = RegisterRequest(email, password)
            val result = authRepository.register(request)

            _isLoading.value = false  // скрыть загрузку

            // Обрабатываем результат
            result.onSuccess { response ->
                if (response.id != null) {
                    _isRegistrationSuccess.value = true
                } else {
                    _errorMessage.value = response.error ?: "Ошибка регистрации"
                }
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Неизвестная ошибка"
            }
        }
    }

    // Очистить ошибку
    fun clearError() {
        _errorMessage.value = null
    }

    // Сбросить флаг успеха
    fun resetRegistrationSuccess() {
        _isRegistrationSuccess.value = false
    }
}