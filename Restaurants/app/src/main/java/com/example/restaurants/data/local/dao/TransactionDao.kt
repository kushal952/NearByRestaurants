package com.example.restaurants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurants.data.local.poco.Transactions

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<Transactions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(transactions: Transactions)

    @Query("SELECT * FROM transactions WHERE name = :name AND restaurant_id = :restaurantId LIMIT 1")
    suspend fun contains(name: String, restaurantId: String): Transactions?
}



