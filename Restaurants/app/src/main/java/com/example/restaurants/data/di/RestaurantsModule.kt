package com.example.restaurants.data.di

import android.content.Context
import androidx.room.Room
import com.example.restaurants.BuildConfig
import com.example.restaurants.data.local.dao.AddressDao
import com.example.restaurants.data.local.dao.CategoryDao
import com.example.restaurants.data.local.dao.CoordinatesDao
import com.example.restaurants.data.local.dao.LocationDao
import com.example.restaurants.data.local.dao.RestaurantDao
import com.example.restaurants.data.local.dao.TransactionDao
import com.example.restaurants.data.local.database.RestaurantDb
import com.example.restaurants.data.remote.RestaurantClassApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantsModule {

    @Provides
    fun provideRestaurantDao(database: RestaurantDb): RestaurantDao {
        return database.restaurantDao
    }
    @Provides
    fun provideAddressDao(database: RestaurantDb): AddressDao {
        return database.addressDao
    }
    @Provides
    fun provideCategoryDao(database: RestaurantDb): CategoryDao {
        return database.categoryDao
    }
    @Provides
    fun provideCoordinateDao(database: RestaurantDb): CoordinatesDao {
        return database.coordinatesDao
    }
    @Provides
    fun provideLocationDao(database: RestaurantDb): LocationDao {
        return database.locationDao
    }
    @Provides
    fun provideTransactionDao(database: RestaurantDb): TransactionDao {
        return database.transactionDao
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(
        @ApplicationContext appContext: Context
    ): RestaurantDb {
        return Room.databaseBuilder(appContext, RestaurantDb::class.java, "restaurant_database").build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
//        val httpLoggingInterceptor = HttpLoggingInterceptor()
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL).build()
    }

    @Provides
    fun provideRetrofitRestaurantsApi(retrofit: Retrofit): RestaurantClassApiService {
        return retrofit.create(RestaurantClassApiService::class.java)
    }
}








