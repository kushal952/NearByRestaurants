package com.example.restaurants.data.local.poco

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("location")
data class Location(
    @PrimaryKey
    val id: String,
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val state: String,
    @SerializedName("zip_code")
    val zipCode: String
)