package com.example.dev.foodrunner.ui.restaurant_dish

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dev.foodrunner.Event
import com.example.dev.foodrunner.model.MainRepository
import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.pojo.RestaurantDish
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantDishViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _snackbarMsgEvent = MutableLiveData<Event<String>>()
    val snackbarMsgEvent: LiveData<Event<String>> = _snackbarMsgEvent

    private val _restaurantDish = MutableLiveData<Event<List<RestaurantDish>>>()
    val restaurantDish: LiveData<Event<List<RestaurantDish>>> = _restaurantDish

    fun getDishes(id: Int) {
        _loading.value = true
        viewModelScope.launch {
            handleResponse(repository.getRestaurant(id))
        }
    }

    private fun handleResponse(responseState: ResponseState<List<RestaurantDish>>) {
        when (responseState) {
            is ResponseState.Success -> {
                _loading.value = false
                _restaurantDish.value = Event(responseState.data)
            }
            is ResponseState.Error -> {
                _loading.value = false
                _snackbarMsgEvent.value = Event(responseState.exception.message!!)
            }
            ResponseState.Loading -> _loading.value = true
        }
    }

}