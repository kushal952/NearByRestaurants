package com.example.restaurants.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.restaurants.domain.poco.Location
import com.example.restaurants.domain.poco.Restaurant
import kotlin.math.roundToInt


@Composable
fun RestaurantsScreen(restaurantScreenState: RestaurantScreenState, onDistanceValueSlider: (distanceValue: Float) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        DistanceSlider {
            onDistanceValueSlider(it)
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if(!restaurantScreenState.isLoading) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        vertical = 8.dp,
                        horizontal = 8.dp
                    )
                ) {
                    items(restaurantScreenState.restaurant) { restaurant ->
                        RestaurantsItem(restaurant)
                    }
                }
            }
            if(restaurantScreenState.isLoading) {
                CircularProgressIndicator()
            }
            if(restaurantScreenState.error.isNotEmpty() && !restaurantScreenState.isLoading) {
                Text(text = restaurantScreenState.error)
            }
        }
    }
}

@Composable
fun RestaurantsItem(item: Restaurant) {

    val location: Location = item.location
    val addressLine = if(location.address1.isNotEmpty() || location.address2.isNotEmpty() || location.address3.isNotEmpty()) {
        location.address1.plus(", ${location.address2}").plus(", ${location.address3}")
    } else {
        "Not Available"
    }

    Card(elevation = CardDefaults.elevatedCardElevation(4.dp), modifier = Modifier.padding(8.dp).wrapContentHeight()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            RestaurantDetails(title = item.name, address = addressLine, imageUrl = item.imageUrl, isOpen = !item.isClosed, rating = item.rating,
                modifier = Modifier.weight(0.85f))
        }
    }
}


@Composable
 fun RestaurantDetails(title: String, imageUrl:String, isOpen: Boolean, rating: Double,
                       address:String, modifier: Modifier, horizontalAlignment: Alignment.Horizontal = Alignment.Start) {
     val isOpenText = if(isOpen) {
         "Currently OPEN"
     } else {
         "Currently CLOSED"
     }
     Row {
         AsyncImage(modifier = Modifier.height(60.dp).width(100.dp).padding(end = 8.dp),
             model = imageUrl, contentScale = ContentScale.Crop, contentDescription = "")
         Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
             Text(text = title, style = MaterialTheme.typography.titleMedium)
             Text(text = address, style = MaterialTheme.typography.bodyMedium)
             Text(text = isOpenText, style = MaterialTheme.typography.bodyMedium)
         }
             Row(modifier = Modifier.background(color = Color(255,87,51), CircleShape)
                 .height(60.dp).width(60.dp), horizontalArrangement = Arrangement.Center,
                 verticalAlignment = Alignment.CenterVertically) {
                 Text(text = rating.toString(), fontSize = TextUnit(20f, TextUnitType.Sp), color = Color.White)
             }
     }
}


@Composable
fun DistanceSlider(onDistanceValueSlider: (distanceValue: Float) -> Unit) {
    var sliderValue by remember { mutableFloatStateOf(100f) }

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Radius Selector", fontWeight = FontWeight.Bold)
            Text(text = "${sliderValue.roundToInt()} KM")
        }
        Slider(
            value = sliderValue,
            onValueChange = {
                val distanceValue = (it / 10).roundToInt() * 10f
                sliderValue = distanceValue
                onDistanceValueSlider(distanceValue)
            },
            valueRange = 100f..5000f,
            steps = 489
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "100 KM")
            Text(text = "5000 KM")
        }
    }
}




