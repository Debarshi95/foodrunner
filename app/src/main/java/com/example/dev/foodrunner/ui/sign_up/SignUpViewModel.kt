package com.example.dev.foodrunner.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dev.foodrunner.Event
import com.example.dev.foodrunner.model.MainRepository
import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.pojo.User
import com.example.dev.foodrunner.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _navigateToRestaurantFragEvent = MutableLiveData<Event<Boolean>>()
    val navigateToRestaurantFragEvent: LiveData<Event<Boolean>> = _navigateToRestaurantFragEvent

    private val _navigateToSignInFragEvent = MutableLiveData<Event<Unit>>()
    val navigateToSignInFragment: LiveData<Event<Unit>> = _navigateToSignInFragEvent

    private val _userLoggedStatus = MutableLiveData<Event<Boolean>>()
    val userLoggedStatus: LiveData<Event<Boolean>> = _userLoggedStatus

    private val _snackBarMessageEvent = MutableLiveData<Event<String>>()
    val snackBarEventMessage: LiveData<Event<String>> = _snackBarMessageEvent

    init {
        _userLoggedStatus.value = Event(repository.getUserLoggedInState())
    }

    fun setNavigateToSignInFragEvent() {
        _navigateToSignInFragEvent.value = Event(Unit)
    }

    fun handleSignUp(vararg inputs: String) {
        if (!handleUserInput { isTextEmpty(inputs[0]) }) {
            _snackBarMessageEvent.value = Event("Name is required")
        } else if (!handleUserInput { isEmailValid(inputs[1]) }) {
            _snackBarMessageEvent.value = Event("Email is not valid")
        } else if (!handleUserInput { isMobileNumberValid(inputs[2]) }) {
            _snackBarMessageEvent.value = Event("Mobile Number is required")
        } else if (!handleUserInput { isTextEmpty(inputs[3]) }) {
            _snackBarMessageEvent.value = Event("Address is required")
        } else if (!handleUserInput { isTextEmpty(inputs[4]) }) {
            _snackBarMessageEvent.value = Event("Password is required")
        } else if (!handleUserInput { doesPasswordMatch(inputs[4], inputs[5]) }) {
            _snackBarMessageEvent.value = Event("Passwords do not match")
        } else {
            userSignUp(
                User(
                    name = inputs[0],
                    email = inputs[1],
                    mobileNumber = inputs[2],
                    address = inputs[3],
                    password = inputs[5]
                )
            )
        }
    }

    private fun userSignUp(user: User) {
        _loading.value = true
        viewModelScope.launch {
            handleResponse(repository.userSignUp(user))
        }
    }

    private fun handleResponse(response: ResponseState<User>) {
        when (response) {
            is ResponseState.Success -> {
                _loading.value = false
                _navigateToRestaurantFragEvent.value = Event(true)
            }
            is ResponseState.Error -> {
                _loading.value = false
                _snackBarMessageEvent.value = Event(response.exception.message.toString())
            }
            ResponseState.Loading -> _loading.value = true
        }
    }
}