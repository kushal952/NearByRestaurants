package com.example.restaurants.data.remote

import com.example.restaurants.data.local.poco.Category
import java.util.UUID

data class Category(
    val alias: String,
    val title: String
)

fun createCategoryList(categories: List<com.example.restaurants.data.remote.Category>, restaurantId: String): List<Category> {
    return ArrayList<Category>().apply {
        categories.forEach { category ->
            val categoryId = UUID.randomUUID().toString()
            with(category) {
                val aliasValue = alias ?: ""
                val titleValue = title ?: ""

                val categoryObj = Category(categoryId, aliasValue, titleValue, restaurantId)
                add(categoryObj)
            }
        }
    }
}





