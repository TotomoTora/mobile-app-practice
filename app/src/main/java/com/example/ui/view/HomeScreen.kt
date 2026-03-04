package com.example.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Главная",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Поиск"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Корзина"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            item { CategoriesSection() }
            item { PopularSection() }
            item { BestSellerSection() }
            item { PromotionsSection() }
        }
    }
}

@Composable
fun CategoriesSection() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "Категории",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            val categories = listOf("Все", "Outdoor", "Tennis")
            items(categories) { category ->
                CategoryChip(
                    name = category,
                    isSelected = category == "Все"
                )
            }
        }
    }
}

@Composable
fun CategoryChip(
    name: String,
    isSelected: Boolean
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF6200EE) else Color(0xFFF0F0F0),
        modifier = Modifier
            .height(36.dp)
            .wrapContentWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = name,
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

@Composable
fun PopularSection() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "Популярное",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            val filters = listOf("Всё", "Outdoor", "Tennis")
            items(filters) { filter ->
                FilterChip(
                    name = filter,
                    isSelected = filter == "Всё"
                )
            }
        }
    }
}

@Composable
fun FilterChip(
    name: String,
    isSelected: Boolean
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) Color(0xFF6200EE) else Color.Transparent,
        border = if (!isSelected) BorderStroke(1.dp, Color.Gray) else null,
        modifier = Modifier
            .height(32.dp)
            .wrapContentWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                text = name,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BestSellerSection() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(4) { index ->
                ProductCard(
                    brand = "BEST SELLER",
                    name = "Nike Air Max",
                    price = 752.00,
                    imageRes = R.drawable.ic_launcher_background
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    brand: String,
    name: String,
    price: Double,
    imageRes: Int
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE0E0E0)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Фото",
                            color = Color.Gray
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = brand,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "₽${String.format("%.2f", price)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6200EE)
                    )

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF6200EE),
                        modifier = Modifier.size(28.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Добавить",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PromotionsSection() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "Акции",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            item {
                PromotionCard(
                    title = "Summer Sale",
                    description = "15% OFF",
                    backgroundColor = Color(0xFFFF9800),
                    isNew = false
                )
            }

            item {
                PromotionCard(
                    title = "NEW!",
                    description = "",
                    backgroundColor = Color(0xFF4CAF50),
                    isNew = true
                )
            }
        }
    }
}

@Composable
fun PromotionCard(
    title: String,
    description: String,
    backgroundColor: Color,
    isNew: Boolean
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                if (description.isNotEmpty()) {
                    Text(
                        text = description,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                if (isNew) {
                    Text(
                        text = "Новинка",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    ProductCard(
        brand = "BEST SELLER",
        name = "Nike Air Max",
        price = 752.00,
        imageRes = R.drawable.ic_launcher_background
    )
}