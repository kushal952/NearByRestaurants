package com.example.restaurants.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurants.presentation.list.RestaurantsScreen
import com.example.restaurants.presentation.list.RestaurantsViewModel
import com.example.restaurants.ui.theme.RestaurantsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Restaurant Project
@AndroidEntryPoint
class MainActivity @Inject constructor(): ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantsTheme {
                RestaurantApp()
            }
        }
    }
}

@Composable
private fun RestaurantApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "restaurants") {
        composable(route = "restaurants") {
            val viewModel: RestaurantsViewModel = hiltViewModel()
            RestaurantsScreen(viewModel.state.value, { distanceValue ->
                viewModel.distance.value = distanceValue
                viewModel.getRestaurants()
            })
        }
    }
}
