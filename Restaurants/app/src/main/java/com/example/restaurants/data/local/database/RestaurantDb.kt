package com.example.restaurants.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restaurants.data.local.dao.AddressDao
import com.example.restaurants.data.local.dao.CategoryDao
import com.example.restaurants.data.local.dao.CoordinatesDao
import com.example.restaurants.data.local.dao.LocationDao
import com.example.restaurants.data.local.dao.RestaurantDao
import com.example.restaurants.data.local.dao.TransactionDao
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.Coordinates
import com.example.restaurants.data.local.poco.DisplayAddress
import com.example.restaurants.data.local.poco.LocalRestaurant
import com.example.restaurants.data.local.poco.Location
import com.example.restaurants.data.local.poco.Transactions

@Database(entities = [LocalRestaurant::class, Location::class, Coordinates::class, Category::class, DisplayAddress::class,
    Transactions::class], version = 1, exportSchema = false)
abstract class RestaurantDb: RoomDatabase() {
    abstract val restaurantDao: RestaurantDao
    abstract val addressDao: AddressDao
    abstract val categoryDao: CategoryDao
    abstract val coordinatesDao: CoordinatesDao
    abstract val locationDao: LocationDao
    abstract val transactionDao: TransactionDao
}