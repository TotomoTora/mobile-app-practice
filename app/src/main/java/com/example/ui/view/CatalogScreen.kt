package com.example.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    // Логирование для отладки - проверяем наличие данных пользователя
    Log.d("CATALOG", "sessionUserId=$sessionUserId token=${token?.take(10)}")


    // Основная верстка экрана
    Column(
        modifier = Modifier
            .fillMaxSize() // Занимает весь доступный размер
            .background(Color(0xFFF5F7FB)) // Фоновый цвет всего экрана
    ) {
        // Верхняя панель с заголовком и кнопкой назад
        TopAppBar(
            title = {
                Text(
                    text = "", // Отображение названия выбранной категории
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                // Кнопка возврата на предыдущий экран
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = "Назад"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFF5F7FB) // Цвет фона панели
            )
        )

        // Блок с категориями для горизонтальной прокрутки
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Заголовок секции категорий
            Text(
                text = "Категории",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF9E9E9E)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}
