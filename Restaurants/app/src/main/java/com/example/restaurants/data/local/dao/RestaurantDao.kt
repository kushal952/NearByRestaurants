package com.example.restaurants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.LocalRestaurant
import com.example.restaurants.data.remote.RestaurantClass

@Dao
interface RestaurantDao {
    @Query("SELECT * FROM restaurants")
    suspend fun getAll(): List<LocalRestaurant>

    @Query("SELECT * FROM restaurants WHERE distance >= 0 AND distance <= :distance ")
    suspend fun getAll(distance: Double): List<LocalRestaurant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(restaurant: LocalRestaurant)

    @Query("SELECT * FROM restaurants WHERE " +
            "id = :id AND alias = :alias AND coordinates_id = :coordinatesId AND " +
            "display_phone = :displayPhone AND distance = :distance AND image_url = :imageUrl AND " +
            "is_closed = :isClosed AND location_id = :locationId AND name = :name AND " +
            "phone = :phone AND price = :price AND rating = :rating AND " +
            "review_count = :reviewCount AND url = :url  LIMIT 1")
    suspend fun contains(id: String, alias: String, coordinatesId: String, displayPhone: String,
                         distance: Double, imageUrl: String, isClosed: Boolean,
                         locationId: String, name: String, phone: String,
                         price: String, rating: Double, reviewCount: Int, url: String,): LocalRestaurant?

}



