package com.example.restaurants.data.local.poco

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class LocalRestaurant(
    @PrimaryKey
    val id: String,
    val alias: String,
    @ColumnInfo("coordinates_id")
    val coordinatesId: String,
    @ColumnInfo("display_phone")
    val displayPhone: String,
    val distance: Double,
    @ColumnInfo("image_url")
    val imageUrl: String,
    @ColumnInfo("is_closed")
    val isClosed: Boolean,
    @ColumnInfo("location_id")
    val locationId: String,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    @ColumnInfo("review_count")
    val reviewCount: Int,
    val url: String
)









