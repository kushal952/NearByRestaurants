package com.example.restaurants.domain.poco

import com.example.restaurants.data.local.dao.CategoryDao
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.LocalRestaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Category(
    val alias: String,
    val title: String
)


suspend fun mapCategoryObject(categoryDao: CategoryDao, localRestaurant: LocalRestaurant): List<com.example.restaurants.domain.poco.Category> {
    return withContext(Dispatchers.IO) {
        val categories: List<Category> = categoryDao.getAll()
        categories.filter { it.id == localRestaurant.id }.map { category ->
            Category(category.alias, category.title)
        }
    }
}