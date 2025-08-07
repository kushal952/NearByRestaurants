package com.example.restaurants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.DisplayAddress

@Dao
interface AddressDao {

    @Query("SELECT * FROM display_address")
    suspend fun getAll(): List<DisplayAddress>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(displayAddress: DisplayAddress)

    @Query("SELECT * FROM display_address WHERE address_line = :addressLine LIMIT 1")
    suspend fun contains(addressLine: String): DisplayAddress?
}