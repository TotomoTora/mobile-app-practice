package com.example.app.presentation.ui

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Связываем код с дизайном
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Настраиваем обработчики
        setupListeners()
    }

    private fun setupListeners() {
        // При изменении чекбокса
        binding.cbTerms.setOnCheckedChangeListener { _, isChecked ->
            // Кнопка активна только если чекбокс отмечен
            binding.btnSignUp.isEnabled = isChecked
        }

        // При нажатии на кнопку регистрации
        binding.btnSignUp.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            // Проверяем заполнение полей
            if (name.isEmpty()) {
                showErrorDialog("Введите имя")
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                showErrorDialog("Введите email")
                return@setOnClickListener
            }

            // Проверка email
            if (!isValidEmail(email)) {
                showErrorDialog("Некорректный email. Используйте формат: имя@домен.ru")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                showErrorDialog("Введите пароль")
                return@setOnClickListener
            }

            // TODO: Здесь будет отправка на сервер
            Toast.makeText(this, "Регистрация...", Toast.LENGTH_SHORT).show()
        }

        // При нажатии на ссылку "Войти"
        binding.tvSignIn.setOnClickListener {
            Toast.makeText(this, "Переход на вход", Toast.LENGTH_SHORT).show()
        }
    }

    // Проверка email
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$")
        return emailPattern.matches(email.lowercase())
    }

    // Показать диалог с ошибкой
    private fun showErrorDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Ошибка")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}