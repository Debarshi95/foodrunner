package com.example.dev.foodrunner.ui.sign_in

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dev.foodrunner.model.RestaurantRepository
import com.example.dev.foodrunner.network.User
import kotlinx.coroutines.launch
import javax.inject.Inject


class SignInViewModel @Inject constructor(private val repository: RestaurantRepository) :
    ViewModel() {

    init {
        Log.i("ViewModel", repository.toString())
        Log.i("ViewModel", "$this")
    }

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUser(user: User) = viewModelScope.launch {
        repository.getUser(user)
        Log.d("user", "${repository.getUser(user)}")
    }
}