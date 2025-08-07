package com.example.restaurants.data.local.poco

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("display_address")
data class DisplayAddress(
    @PrimaryKey
    val id: String,
    @ColumnInfo("address_line")
    val addressLine: String,
    @ColumnInfo("location_id")
    val locationId: String
)
