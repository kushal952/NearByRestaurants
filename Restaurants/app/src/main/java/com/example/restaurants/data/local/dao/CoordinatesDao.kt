package com.example.restaurants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.Coordinates

@Dao
interface CoordinatesDao {

    @Query("SELECT * FROM coordinates")
    suspend fun getAll(): List<Coordinates>

    @Query("SELECT * FROM coordinates WHERE latitude = :latitude AND longitude = :longitude")
    suspend fun getCoordinate(latitude: Double, longitude: Double): List<Coordinates>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(coordinates: Coordinates)

    @Query("SELECT * FROM coordinates WHERE latitude = :latitude AND longitude = :longitude AND restaurant_id = :restaurantId LIMIT 1")
    suspend fun contains(latitude: Double, longitude: Double, restaurantId: String): Coordinates?
}