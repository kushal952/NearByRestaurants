package com.example.restaurants.domain.poco

import com.example.restaurants.data.local.dao.CoordinatesDao
import com.example.restaurants.data.local.poco.Coordinates
import com.example.restaurants.data.local.poco.LocalRestaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Coordinates (
    val latitude: Double,
    val longitude: Double
)

suspend fun mapCoordinatesObjects(coordinatesDao: CoordinatesDao, localRestaurant: LocalRestaurant): com.example.restaurants.domain.poco.Coordinates {
    return withContext(Dispatchers.IO) {
        val coordinates: List<Coordinates> = coordinatesDao.getAll()
        coordinates.filter { it.restaurantId == localRestaurant.id }.map { coordinate ->
            Coordinates(coordinate.latitude, coordinate.longitude)
        }.first()
    }
}
