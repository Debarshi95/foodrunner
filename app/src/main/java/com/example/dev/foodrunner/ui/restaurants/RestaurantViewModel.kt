package com.example.dev.foodrunner.ui.restaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dev.foodrunner.Event
import com.example.dev.foodrunner.model.MainRepository
import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.pojo.Restaurant
import kotlinx.coroutines.launch
import javax.inject.Inject

class RestaurantViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _snackbarMsgEvent = MutableLiveData<Event<String>>()
    val snackbarMsgEvent: LiveData<Event<String>> = _snackbarMsgEvent

    private val _restaurantListData = MutableLiveData<Event<List<Restaurant>>>()
    val restaurantList: LiveData<Event<List<Restaurant>>> = _restaurantListData

    private val _moveToDishFragEvent = MutableLiveData<Event<Restaurant>>()
    val moveToDishFragEvent: LiveData<Event<Restaurant>> = _moveToDishFragEvent

    fun getRestaurants() {
        _loading.value = true
        viewModelScope.launch {
            handleResponse(repository.getRestaurants())
        }
    }

    fun setMoveToDishFragEvent(restaurant: Restaurant) {
        _moveToDishFragEvent.value = Event(restaurant)
    }

    private fun handleResponse(response: ResponseState<List<Restaurant>>) {
        when (response) {
            is ResponseState.Success -> {
                _loading.value = false
                _restaurantListData.value = Event(response.data)
            }
            is ResponseState.Error -> {
                _loading.value = false
                _snackbarMsgEvent.value = Event(response.exception.message!!)
            }
            ResponseState.Loading -> _loading.value = true
        }
    }

}