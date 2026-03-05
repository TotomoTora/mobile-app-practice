package com.example.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.data.RetrofitInstance
import com.example.data.UserSession
import com.example.practice.R
import kotlin.collections.mapNotNull
import kotlin.collections.toSet

/**
 * Экран избранного - отображает товары, добавленные пользователем в избранное
 * Позволяет просматривать и удалять товары из избранного
 *
 * @param navController навигационный контроллер для переходов между экранами
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController) {
    // Получение данных авторизации текущего пользователя
    val token = UserSession.accessToken
    val userId = UserSession.userId
    // Создание корутин скоупа для асинхронных операций
    rememberCoroutineScope()

    // Состояния экрана
    var products by remember { mutableStateOf<List<CatalogProduct>>(emptyList()) } // Список избранных товаров
    var isLoading by remember { mutableStateOf(false) } // Флаг загрузки данных

    /**
     * Загрузка списка избранных товаров при первом входе на экран
     * Срабатывает при изменении token или userId
     */
    LaunchedEffect(token, userId) {
        // Проверка авторизации
        if (token == null || userId == null) return@LaunchedEffect
        isLoading = true
        try {
            val service = RetrofitInstance.userManagementService

            // Загрузка ID избранных товаров пользователя
            val favs = service.getFavourites(
                authHeader = "Bearer $token",
                userIdFilter = "eq.$userId", // Фильтр по ID пользователя
                select = "product_id" // Запрашиваем только ID товаров для оптимизации
            )

            // Если избранное пустое, очищаем список
            if (favs.isEmpty()) {
                products = emptyList()
            } else {
                // Загружаем все товары для получения полной информации
                val allProducts = service.getProducts(
                    authHeader = "Bearer $token"
                )
                // Создаем множество ID избранных товаров
                val favIds = favs.mapNotNull { it.product_id }.toSet()
            }
        } finally {
            isLoading = false
        }
    }
    // Scaffold - базовая структура экрана с нижней навигацией
    Scaffold(
        bottomBar = { BottomBar(navController = navController, currentRoute = "favorite") },
        containerColor = Color(0xFFF5F7FB) // Фоновый цвет
    ) { innerPadding ->
        /**
         * Корректировка отступов от Scaffold
         * Убираем верхний отступ, оставляем только снизу для правильного расположения контента
         */
        val contentPadding = PaddingValues(
            start = innerPadding.calculateLeftPadding(LayoutDirection.Ltr),
            end = innerPadding.calculateRightPadding(LayoutDirection.Ltr),
            bottom = innerPadding.calculateBottomPadding()
        )

        // Основной контент экрана
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F7FB))
        ) {
            // Верхняя панель с заголовком и навигацией
            TopAppBar(
                title = {
                    Text(
                        text = "Избранное",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    // Кнопка назад
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    // Иконка-индикатор избранного в правой части панели
                    Icon(
                        painter = painterResource(id = R.drawable.ic_heart_filled),
                        contentDescription = "Favorite",
                        tint = Color(0xFFDD4B4B), // Красный цвет
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(20.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F7FB) // Прозрачный фон
                )
            )
        }
    }
}

/**
 * Карточка избранного товара для отображения в сетке
 * Визуально похожа на карточку в каталоге, но с особым поведением для удаления из избранного
 *
 * @param product данные товара для отображения
 * @param onRemove колбэк, вызываемый при нажатии на сердечко для удаления из избранного
 */
@Composable
private fun FavoriteProductCard(
    product: CatalogProduct,
    onRemove: () -> Unit
) {
    // Карточка товара
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp)) // Скругление углов
            .background(Color.White) // Белый фон
            .padding(10.dp) // Внутренние отступы
    ) {
        Column {
            // Верхний ряд с иконкой избранного
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Выравнивание по правому краю
            ) {
                // Контейнер для иконки сердечка
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color.White), // Белый фон для контраста
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(modifier = Modifier.height(4.dp))

                    // Контейнер для изображения товара
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFF2F4F7)), // Светло-серый фон
                        contentAlignment = Alignment.Center
                    ) {
                        // Изображение товара
                        Image(
                            painter = painterResource(id = product.imageRes),
                            contentDescription = product.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit // Масштабирование с сохранением пропорций
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    // Название товара
                    Text(
                        text = product.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Нижний ряд с ценой и кнопкой добавления в корзину
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Цена товара
                        Text(
                            text = "₽${product.price}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF333333),
                            modifier = Modifier.weight(1f) // Занимает все доступное пространство слева
                        )
                    }
                }
            }
        }
    }
}