package com.example.ui.view

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.data.UserSession
import com.example.practice.R

/**
 * Класс данных, представляющий категорию товаров в каталоге
 * @param id уникальный идентификатор категории в базе данных
 * @param title отображаемое название категории для пользователя
 */
data class CatalogCategory(
    val id: String,
    val title: String
)
/**
 * Класс данных, представляющий товар в каталоге
 * @param id уникальный идентификатор товара
 * @param title название товара
 * @param price цена товара
 * @param categoryId идентификатор категории, к которой относится товар
 * @param isBestSeller флаг, указывающий является ли товар бестселлером
 * @param imageRes ресурс изображения товара
 * @param isFavorite флаг, находится ли товар в избранном у текущего пользователя
 * @param description подробное описание товара из базы данных
 */
data class CatalogProduct(
    val id: String,
    val title: String,
    val price: Double,
    val categoryId: String?,
    val isBestSeller: Boolean,
    val imageRes: Int,
    val isFavorite: Boolean = false,
    val description: String = ""
)
/**
 * Главный экран каталога товаров
 * @param navController навигационный контроллер для переходов между экранами
 * @param initialCategoryTitle начальная выбранная категория (по умолчанию "Outdoor")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavHostController,
    initialCategoryTitle: String = "Outdoor"
) {
    val categories = listOf(
        CatalogCategory("all", "Все"),
        CatalogCategory("ea4ed603-8cbe-4d57-a359-b6b843a645bc", "Outdoor"),
        CatalogCategory("4f3a690b-41bf-4fca-8ffc-67cc385c6637", "Tennis"),
        CatalogCategory("76ab9d74-7d5b-4dee-9c67-6ed4019fa202", "Men"),
        CatalogCategory("8143b506-d70a-41ec-a5eb-3cf09627da9e", "Women")
    )

    // Получение данных текущего пользователя из глобального объекта сессии
    val sessionUserId = UserSession.userId
    val token = UserSession.accessToken
    // Создание корутин скоупа для выполнения асинхронных операций
    val scope = rememberCoroutineScope()

    // Состояния экрана
    var allProducts by remember { mutableStateOf<List<com.example.examen.ui.view.CatalogProduct>>(emptyList()) } // Список всех товаров
    var selectedCategory by remember { mutableStateOf(initialCategoryTitle) } // Выбранная категория
    var isLoading by remember { mutableStateOf(false) } // Флаг загрузки данных

    // Логирование для отладки - проверяем наличие данных пользователя
    Log.d("CATALOG", "sessionUserId=$sessionUserId token=${token?.take(10)}")
}
