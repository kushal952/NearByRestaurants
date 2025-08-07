package com.example.restaurants.data.remote

import com.example.restaurants.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestaurantClassApiService {

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("v3/businesses/search")
    suspend fun getRestaurants(@Query("location") location: String = "NYC", @Query("limit") limit: Int = 15,
                               @Query("sort_by") sortBy: String = "best_match"): Businesses

    @GET("restaurants.json?orderBy=\"r_id\"")
    suspend fun getRestaurant(@Query("equalTo") id: Int): Map<String, RestaurantClass>

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}")
    @GET("v3/businesses/search")
    suspend fun getRestaurantsBasedOnLatLng(@Query("latitude") latitude: Double,
                               @Query("longitude") double: Double, @Query("limit") limit: Int = 15): Businesses


}







