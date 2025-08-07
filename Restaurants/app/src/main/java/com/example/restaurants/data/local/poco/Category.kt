package com.example.restaurants.data.local.poco

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: String,
    val alias: String,
    val title: String,
    @ColumnInfo("restaurant_id")
    val restaurantId: String
)









