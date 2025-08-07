package com.example.restaurants.domain

import com.example.restaurants.data.RestaurantRepository
import com.example.restaurants.domain.poco.Restaurant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetInitialRestaurantsUseCase @Inject constructor(private val repository: RestaurantRepository) {

    suspend fun getRestaurants(distance: Double): List<Restaurant> {
        repository.loadRestaurants(distance)
        val deferred = CoroutineScope(Dispatchers.IO).async {
            repository.getRestaurants(distance)
        }
        return deferred.await()
    }
}












