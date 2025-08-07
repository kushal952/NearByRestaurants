package com.example.restaurants.presentation.list

import com.example.restaurants.domain.poco.Restaurant

data class RestaurantScreenState(
    val restaurant: List<Restaurant>, val isLoading: Boolean,
    var error: String = "Unable to fetch restaurants. Please try again after sometime!",
)
