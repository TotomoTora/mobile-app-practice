package com.example.ui.view

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.practice.R


data class CatalogCategory(
    val id: String,
    val title: String
)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavHostController,
    initialCategoryTitle: String = "Outdoor"
) {

}
