package com.example.restaurants.data

import com.example.restaurants.data.local.dao.AddressDao
import com.example.restaurants.data.local.dao.CategoryDao
import com.example.restaurants.data.local.dao.CoordinatesDao
import com.example.restaurants.data.local.dao.LocationDao
import com.example.restaurants.data.local.dao.RestaurantDao
import com.example.restaurants.data.local.dao.TransactionDao
import com.example.restaurants.data.local.poco.Coordinates
import com.example.restaurants.data.local.poco.DisplayAddress
import com.example.restaurants.data.local.poco.LocalRestaurant
import com.example.restaurants.data.local.poco.Location
import com.example.restaurants.data.remote.RestaurantClassApiService
import com.example.restaurants.data.remote.createCategoryList
import com.example.restaurants.domain.poco.Category
import com.example.restaurants.domain.poco.Restaurant
import com.example.restaurants.domain.poco.createTransactionList
import com.example.restaurants.domain.poco.mapAddressesObject
import com.example.restaurants.domain.poco.mapCategoryObject
import com.example.restaurants.domain.poco.mapCoordinatesObjects
import com.example.restaurants.domain.poco.mapLocationObject
import com.example.restaurants.domain.poco.mapTransactionObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.UUID
import javax.inject.Inject

class RestaurantRepository @Inject constructor() {

    @Inject
    lateinit var restClassInterface: RestaurantClassApiService
    @Inject
    lateinit var restaurantsDao: RestaurantDao
    @Inject
    lateinit var addressDao: AddressDao
    @Inject
    lateinit var categoryDao: CategoryDao
    @Inject
    lateinit var locationDao: LocationDao
    @Inject
    lateinit var transactionDao: TransactionDao
    @Inject
    lateinit var coordinatesDao: CoordinatesDao

    suspend fun loadRestaurants(distance: Double) {
        withContext(Dispatchers.IO) {
            try {
                refreshCache()
            } catch (e: Exception) {
                when(e) {
                    is UnknownHostException,
                    is ConnectException,
                    is HttpException -> {
                        if(restaurantsDao.getAll().isEmpty()) {
                            throw Exception("Something went wrong We have no data")
                        }
                    }
                    else -> {
                        throw e
                    }
                }
            }
        }
    }

    suspend fun getRestaurants(distance: Double = 5000.0): List<Restaurant> {
        val restaurantsListLocal = ArrayList<Restaurant>()
        val restaurants: List<LocalRestaurant> = restaurantsDao.getAll()

        restaurants.forEach { restaurant ->
            with(restaurant) {
                if(restaurant.distance <= distance) {
                    val categoriesLocal: List<Category> = mapCategoryObject(categoryDao,this)
                    val coordinatesLocal: com.example.restaurants.domain.poco.Coordinates = mapCoordinatesObjects(coordinatesDao, this)
                    val addressesLocal = mapAddressesObject(addressDao, this)
                    val transactionLocal = mapTransactionObject(transactionDao, this)
                    val locationLocal: com.example.restaurants.domain.poco.Location = mapLocationObject(locationDao, this, addressesLocal)

                    val restaurantLocal = Restaurant(id, alias, categoriesLocal,
                        coordinatesLocal, displayPhone, distance,
                        imageUrl, isClosed, locationLocal,
                        name, phone, price,
                        rating, reviewCount, transactionLocal, url)

                    if(restaurantsListLocal.size < 15) {
                        restaurantsListLocal.add(restaurantLocal)
                    }
                }

            }
        }
        return restaurantsListLocal
    }

    private suspend fun refreshCache() {
        val latitude: Double = 40.70274718768062
        val longitude: Double = -73.99343490196397

        val remoteBusinessesClass = restClassInterface.getRestaurantsBasedOnLatLng(latitude, longitude)

        remoteBusinessesClass.businesses.forEach { restaurantClass ->

                var coordinateId = UUID.randomUUID().toString()
                var locationId = UUID.randomUUID().toString()
                val displayAddressId = UUID.randomUUID().toString()

                with(restaurantClass) {
                    val latitude: Double = coordinates.latitude ?: 0.0
                    val longitude: Double = coordinates.longitude ?: 0.0
                    val address1: String = location.address1 ?: ""
                    val address2: String = location.address2 ?: ""
                    val address3: String = location.address3 ?: ""
                    val city: String = location.city ?: ""
                    val country: String = location.country ?: ""
                    val state: String = location.state ?: ""
                    val zipCode: String = location.zipCode ?: ""
                    val restaurantId: String = id ?: ""
                    val aliasValue: String = alias ?: ""
                    val displayPhoneValue: String = displayPhone ?: ""
                    val distanceValue: Double = distance ?: 0.0
                    val imageUrlValue: String = imageUrl ?: ""
                    val isClosedValue: Boolean = isClosed ?: false
                    val nameValue: String = name ?: ""
                    val phoneValue: String = phone ?: ""
                    val priceValue: String = price ?: ""
                    val ratingValue: Double = rating ?: 0.0
                    val reviewCountValue: Int = reviewCount ?: 0


//                    CoroutineScope(Dispatchers.IO).launch {
                        val coordinate = Coordinates(coordinateId, latitude, longitude, restaurantId)
                        val location = Location(locationId, address1, address2, address3, city, country, state, zipCode)
                        val addressLine = address1.plus(", ${address2}").plus(", ${address3}")
                        val displayAddress = DisplayAddress(displayAddressId, addressLine, locationId)
                        val transactionsList = createTransactionList(transactions, restaurantId)
                        val categoryList = createCategoryList(categories, restaurantId)
//                        val addressLine = address1.plus(", ${address2}").plus(", ${address3}")

                        transactionsList.forEach {
                            val transaction = transactionDao.contains(it.name, it.restaurantId)
                            if(transaction == null)
                                transactionDao.add(it)
                        }
                        categoryList.forEach {
                            val category = categoryDao.contains(it.title, it.alias, it.restaurantId)
                            if(category == null) {
                                categoryDao.add(it)
                            }
                        }

                        val coordinateExists = coordinatesDao.contains(coordinate.latitude, coordinate.longitude, coordinate.restaurantId)
                        if(coordinateExists == null) {
                            coordinatesDao.add(coordinate)
                        } else {
                            coordinateId = coordinate.id
                        }

                        val locationExists = locationDao.contains(location.address1,
                            location.address2, location.address3, location.city,
                            location.country, location.state, location.zipCode)

                        if(locationExists == null) {
                            locationDao.add(location)
                        } else {
                            locationId = locationExists.id
                        }

                        val addressExists = addressDao.contains(addressLine)
                        if(addressExists == null)
                            addressDao.add(displayAddress)


                        val localRestaurant = LocalRestaurant(restaurantId, aliasValue, coordinateId,
                            displayPhoneValue, distanceValue, imageUrlValue,
                            isClosedValue, locationId, nameValue,
                            phoneValue, priceValue, ratingValue,
                            reviewCountValue, imageUrlValue)

                        with(localRestaurant) {

                            val restaurantsExists = restaurantsDao.contains(id,alias, coordinatesId,
                                displayPhone, distance, imageUrl, isClosed, this.locationId, name,
                                phone, price, rating, reviewCount, url)

                            if(restaurantsExists == null)
                                restaurantsDao.add(this)
                        }
                    }
                }
//        }
    }
}






