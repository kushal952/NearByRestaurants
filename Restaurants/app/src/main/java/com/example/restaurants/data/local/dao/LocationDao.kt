package com.example.restaurants.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.restaurants.data.local.poco.Category
import com.example.restaurants.data.local.poco.Location

@Dao
interface LocationDao {

    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(location: Location)

    @Query("SELECT * FROM location WHERE address1 = :address1 AND address2 = :address2 " +
            " AND address3 = :address3 AND city = :city AND country = :country AND state = :state" +
            " AND zipCode = :zipCode LIMIT 1")
    suspend fun contains(address1: String, address2: String, address3: String, city:String,
        country: String, state: String, zipCode: String): Location?

}