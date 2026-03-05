package com.example.ui.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.data.RetrofitInstance
import com.example.data.UserSession
import com.example.practice.data.service.ProductDto
import kotlin.collections.firstOrNull
import kotlin.collections.map
import kotlin.collections.mapNotNull
import kotlin.collections.toSet
import com.example.practice.R

/**
 * Экран детальной информации о товаре
 * Отображает подробное описание, изображение, цену и позволяет управлять избранным
 *
 * @param navController навигационный контроллер для переходов между экранами
 * @param productId идентификатор товара, переданный через навигацию
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavHostController,
    productId: String
) {
    // Получение данных авторизации текущего пользователя из глобальной сессии
    val token = UserSession.accessToken
    val userId = UserSession.userId
    // Создание корутин скоупа для выполнения асинхронных операций
    val scope = rememberCoroutineScope()

    // Состояния экрана
    var allProducts by remember { mutableStateOf<List<CatalogProduct>>(emptyList()) } // Все доступные товары
    var current by remember { mutableStateOf<CatalogProduct?>(null) } // Текущий отображаемый товар
    var isLoading by remember { mutableStateOf(false) } // Флаг загрузки данных

    /**
     * Эффект загрузки данных при первом входе на экран
     * Срабатывает при изменении productId, token или userId
     * Загружает все товары и определяет текущий по productId
     */
    LaunchedEffect(productId, token, userId) {
        // Проверка авторизации
        if (token == null || userId == null) return@LaunchedEffect
        isLoading = true
        try {
            val service = RetrofitInstance.userManagementService

            // Загрузка всех товаров
            val products: List<ProductDto> = service.getProducts(
                authHeader = "Bearer $token"
            )

            // Загрузка списка избранного текущего пользователя
            val favs = service.getFavourites(
                authHeader = "Bearer $token",
                userIdFilter = "eq.$userId"
            )

            // Создание множества ID товаров в избранном
            val favSet = favs.mapNotNull { it.product_id }.toSet()

            // Преобразование DTO в модель UI
            val mapped = products.map { p ->
                CatalogProduct(
                    id = p.id,
                    title = p.title,
                    price = p.cost,
                    categoryId = p.category_id,
                    isBestSeller = p.is_best_seller == true,
                    imageRes = R.drawable.img_shoe_blue, // Изображение-заглушка
                    isFavorite = favSet.contains(p.id),
                    description = p.description
                )
            }
            allProducts = mapped
            // Поиск текущего товара по ID, если не найден - берем первый
            current = mapped.firstOrNull { it.id == productId } ?: mapped.firstOrNull()
        } finally {
            isLoading = false
        }
    }
}