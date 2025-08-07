package com.example.restaurants.data.remote

import com.google.gson.annotations.SerializedName

data class RestaurantClass(
    val id: String,
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    @SerializedName("display_phone")
    val displayPhone: String,
    val distance: Double,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("is_closed")
    val isClosed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    @SerializedName("review_count")
    val reviewCount: Int,
    val transactions: List<String>,
    val url: String
)










