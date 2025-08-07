package com.example.restaurants.domain.poco

import android.util.Log
import com.example.restaurants.data.local.dao.LocationDao
import com.example.restaurants.data.local.poco.LocalRestaurant
import com.example.restaurants.data.local.poco.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Location(
    val id: String,
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val displayAddress: List<String>,
    val state: String,
    val zipCode: String
)

suspend fun mapLocationObject(locationDao: LocationDao, localRestaurant: LocalRestaurant, addressLocal: List<String>): com.example.restaurants.domain.poco.Location {
    return withContext(Dispatchers.IO) {
        val locations: List<Location> = locationDao.getAll()
        locations.filter {
            Log.d("&&**&&", "Retrieve SAME SAME ${localRestaurant.locationId} == locationId:${it.id}")
            it.id == localRestaurant.locationId
        }.map { l ->
            Location(l.id, l.address1, l.address2,
                l.address3, l.city, l.country,
                addressLocal , l.state, l.zipCode)
        }.first()
    }
}





