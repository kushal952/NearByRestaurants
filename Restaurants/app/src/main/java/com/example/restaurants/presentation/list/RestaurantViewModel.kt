package com.example.restaurants.presentation.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurants.domain.GetInitialRestaurantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(private val getRestaurantsUseCase: GetInitialRestaurantsUseCase): ViewModel() {

    private val _state = mutableStateOf(RestaurantScreenState(restaurant = listOf(), isLoading = true))
     val state: State<RestaurantScreenState>
        get() = _state
    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _state.value = _state.value.copy(isLoading = false)
    }
    private val _distance: MutableLiveData<Float> = MutableLiveData(100f)
    val distance: MutableLiveData<Float>
        get() = _distance

    init {
        getRestaurants()
    }

    fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            _distance.value?.let {
                val restaurants = getRestaurantsUseCase.getRestaurants(it.toDouble())
                _state.value.error = ""
                _state.value = _state.value.copy(
                    restaurant = restaurants,
                    isLoading = false
                )
            }
        }
    }

}





































