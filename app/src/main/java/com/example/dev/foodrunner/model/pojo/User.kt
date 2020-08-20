package com.example.dev.foodrunner.model.pojo

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("user_id") val id: String = "",
    val name: String = "",
    val email: String = "",
    @SerializedName("mobile_number") val mobileNumber: String = "",
    val address: String = "",
    val password: String = ""
)