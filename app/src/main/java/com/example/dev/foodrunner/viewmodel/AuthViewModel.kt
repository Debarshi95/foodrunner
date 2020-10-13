package com.example.dev.foodrunner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dev.foodrunner.Event
import com.example.dev.foodrunner.model.MainRepository
import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.pojo.ResetPassword
import com.example.dev.foodrunner.model.pojo.User
import com.example.dev.foodrunner.utils.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _userLoggedStatus = MutableLiveData<Event<Boolean>>()
    val userLoggedStatus: LiveData<Event<Boolean>> = _userLoggedStatus

    private val _responseSnackBar = MutableLiveData<Event<String>>()
    val responseSnackBar: LiveData<Event<String>> = _responseSnackBar

    private val _navigateToRestaurantNavGraph = MutableLiveData<Event<Boolean>>()
    val navigateToRestaurantNavGraph: LiveData<Event<Boolean>> = _navigateToRestaurantNavGraph

    private val _navigateToForgotPasswordFragment = MutableLiveData<Event<Unit>>()
    val navigateToForgotPasswordFragment: LiveData<Event<Unit>> = _navigateToForgotPasswordFragment

    private val _navigateToResetPasswordFragment = MutableLiveData<Event<Boolean>>()
    val navigateToResetPasswordFragment: LiveData<Event<Boolean>> = _navigateToResetPasswordFragment

    private val _navigateToSignUpFragment = MutableLiveData<Event<Unit>>()
    val navigateToSignUpFragment: LiveData<Event<Unit>> = _navigateToSignUpFragment

    private val _navigateToSignInFragment = MutableLiveData<Event<Unit>>()
    val navigateToSignInFragment: LiveData<Event<Unit>> = _navigateToSignInFragment

    private val _navigateBackToSignInFragment = MutableLiveData<Event<Boolean>>()
    val navigateBackToSignInFragment: LiveData<Event<Boolean>> = _navigateBackToSignInFragment

    init {
        _userLoggedStatus.value = Event(repository.getUserLoggedInState())
    }


    fun setNavigateToSignUpFragment() {
        _navigateToSignUpFragment.value = Event(Unit)
    }

    fun setNavigateToForgotPasswordFragment() {
        _navigateToForgotPasswordFragment.value = Event(Unit)
    }

    fun setNavigateToSignInFragment() {
        _navigateToSignInFragment.value = Event(Unit)
    }

    fun handleSignIn(inputNumber: String, inputPassword: String) {
        if (!handleUserInput { isTextEmpty(inputNumber) }) {
            _responseSnackBar.value = Event("Mobile number is required")
        } else if (!handleUserInput { isMobileNumberValid(inputNumber) }) {
            _responseSnackBar.value = Event("Mobile number is not valid")
        } else if (!handleUserInput { isTextEmpty(inputPassword) }) {
            _responseSnackBar.value = Event("Password is required")
        } else {
            signIn(User(mobileNumber = inputNumber, password = inputPassword))
        }
    }

    private fun signIn(user: User) {
        viewModelScope.launch {
            _loading.value = true
            handleResponse(repository.signIn(user))
        }

    }

    fun handleSignUp(vararg inputs: String) {
        if (!handleUserInput { isTextEmpty(inputs[0]) }) {
            _responseSnackBar.value = Event("Name is required")
        } else if (!handleUserInput { isEmailValid(inputs[1]) }) {
            _responseSnackBar.value = Event("Email is not valid")
        } else if (!handleUserInput { isMobileNumberValid(inputs[2]) }) {
            _responseSnackBar.value = Event("Mobile Number is required")
        } else if (!handleUserInput { isTextEmpty(inputs[3]) }) {
            _responseSnackBar.value = Event("Address is required")
        } else if (!handleUserInput { isTextEmpty(inputs[4]) }) {
            _responseSnackBar.value = Event("Password is required")
        } else if (!handleUserInput { doesPasswordMatch(inputs[4], inputs[5]) }) {
            _responseSnackBar.value = Event("Passwords do not match")
        } else {
            signUp(
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

    private fun signUp(user: User) {
        _loading.value = true
        viewModelScope.launch {
            handleResponse(repository.signIn(user))
        }
    }

    fun handleResetPassword(
        inputOtp: String,
        inputNewPassword: String,
        inputMobileNumber: String
    ) {
        if (!handleUserInput { isTextEmpty(inputOtp) }) {
            _responseSnackBar.value = Event("OTP is required")
        } else if (!handleUserInput { isTextEmpty(inputNewPassword) }) {
            _responseSnackBar.value = Event("Password is required")
        } else if (!handleUserInput { isMobileNumberValid(inputMobileNumber) }) {
            _responseSnackBar.value = Event("Mobile number is not valid")
        } else {
            resetPassword(
                ResetPassword(
                    otp = inputOtp,
                    newPassword = inputNewPassword,
                    mobileNumber = inputMobileNumber
                )
            )
        }
    }

    private fun resetPassword(resetPasswordData: ResetPassword) {
        _loading.value = true
        viewModelScope.launch {
            handleResponse(repository.resetPassword(resetPasswordData))
        }
    }


    fun handleForgotPassword(inputMobileNumber: String, inputEmail: String) {
        if (!handleUserInput { isTextEmpty(inputMobileNumber) }) {
            _responseSnackBar.value = Event("Mobile number is required")
        } else if (!handleUserInput { isEmailValid(inputEmail) }) {
            _responseSnackBar.value = Event("Email is not valid")
        } else if (!handleUserInput { isMobileNumberValid(inputMobileNumber) }) {
            _responseSnackBar.value = Event("Mobile number is not valid")
        } else {
            forgotPassword(User(mobileNumber = inputMobileNumber, email = inputEmail))
        }
    }

    private fun forgotPassword(user: User) {
        _loading.value = true
        viewModelScope.launch {
            handleResponse(repository.forgotPassword(user))
        }
    }

    private fun <T> handleResponse(response: ResponseState<T>) {
        when (response) {
            is ResponseState.Success -> {
                _loading.value = false
                if (response.data is Boolean) {
                    _navigateBackToSignInFragment.value = Event(true)
                } else if (response.data is User) {
                    _navigateToRestaurantNavGraph.value = Event(true)
                }

            }
            is ResponseState.Error -> {
                _loading.value = false
                _responseSnackBar.value = Event(response.message)
            }
            is ResponseState.Exception -> {
                _loading.value = false
                _responseSnackBar.value = Event(response.exception.toString())
            }
            ResponseState.Loading -> _loading.value = true
        }
    }

}