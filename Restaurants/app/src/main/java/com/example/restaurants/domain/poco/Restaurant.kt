package com.example.restaurants.domain.poco

import com.example.restaurants.data.local.dao.AddressDao
import com.example.restaurants.data.local.poco.DisplayAddress
import com.example.restaurants.data.local.poco.LocalRestaurant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Restaurant(
    val id: String,
    val alias: String,
    val categories: List<Category>,
    val coordinates: Coordinates,
    val displayPhone: String,
    val distance: Double,
    val imageUrl: String,
    val isClosed: Boolean,
    val location: Location,
    val name: String,
    val phone: String,
    val price: String,
    val rating: Double,
    val reviewCount: Int,
    val transactions: List<String>,
    val url: String
)


suspend fun mapAddressesObject(addressDao: AddressDao, localRestaurant: LocalRestaurant): List<String> {
    return withContext(Dispatchers.IO) {
        val addresses: List<DisplayAddress> = addressDao.getAll()
        addresses.filter { it.locationId == localRestaurant.locationId }.map { address ->
            address.addressLine
        }
    }
}
























