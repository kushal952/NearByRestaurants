package com.example.restaurants.data.local.poco

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("transactions")
data class Transactions(
    @PrimaryKey
    val id: String,
    val name: String,
    @ColumnInfo("restaurant_id")
    val restaurantId: String
)
