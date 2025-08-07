package com.example.restaurants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.Transactions


@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(category: Category)

    @Query("SELECT * FROM category WHERE title = :title AND alias = :alias AND restaurant_id = :restaurantId LIMIT 1")
    suspend fun contains(title: String, alias: String, restaurantId: String): Category?
}



