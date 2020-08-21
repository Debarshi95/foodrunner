package com.example.dev.foodrunner.utils

import android.text.TextUtils

fun isEmailValid(inputEmail: String): Boolean {
    return !TextUtils.isEmpty(inputEmail.trim())
            &&
            android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()
}


fun isMobileNumberValid(inputNumber: String): Boolean {
    return android.util.Patterns.PHONE.matcher(inputNumber).matches()
}

fun isTextEmpty(inputText: String): Boolean {
    return !TextUtils.isEmpty(inputText.trim())
}

fun doesPasswordMatch(passwordOne: String, passwordTwo: String): Boolean {
    return passwordOne == passwordTwo
}


fun handleUserInput(cb: () -> Boolean): Boolean {
    return cb()
}