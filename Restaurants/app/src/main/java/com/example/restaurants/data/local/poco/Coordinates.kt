package com.example.restaurants.data.local.poco

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coordinates")
data class Coordinates(
    @PrimaryKey
    val id: String,
    val latitude: Double,
    val longitude: Double,
    @ColumnInfo("restaurant_id")
    val restaurantId: String
)