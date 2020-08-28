package com.example.dev.foodrunner.ui.forgot_password

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dev.foodrunner.Event
import com.example.dev.foodrunner.model.MainRepository
import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.pojo.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(private val repository: MainRepository) :
    ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _navigateToRecoverPasswordFragEvent = MutableLiveData<Event<Boolean>>()
    val navigateToRecoverPasswordFrag: LiveData<Event<Boolean>> =
        _navigateToRecoverPasswordFragEvent

    private val _snackBarMessageEvent = MutableLiveData<Event<String>>()
    val snackBarEventMessage: LiveData<Event<String>> = _snackBarMessageEvent

    fun forgotPassword(user: User) {
        _loading.value = true
        viewModelScope.launch {
            val d = repository.forgotPassword(user)
            Log.i("view", "$d")
            handleResponse(d)
        }
    }

    private fun handleResponse(response: ResponseState<Boolean>) {
        when (response) {
            is ResponseState.Success -> {
                _loading.value = false
                _navigateToRecoverPasswordFragEvent.value = Event(response.data)
            }
            is ResponseState.Error -> {
                _loading.value = false
                _snackBarMessageEvent.value = Event("")
            }
            ResponseState.Loading -> {
                _loading.value = true
            }
        }
    }

}