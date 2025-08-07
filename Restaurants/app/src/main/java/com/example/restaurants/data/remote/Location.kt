package com.example.restaurants.data.remote

import com.google.gson.annotations.SerializedName

data class Location(
    val id: String,
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    @SerializedName("display_address")
    val displayAddress: List<String>,
    val state: String,
    @SerializedName("zip_code")
    val zipCode: String
)







