package com.example.dev.foodrunner.network

import com.google.gson.annotations.SerializedName

data class RestaurantList(
    @SerializedName("data")
    val list: List<Restaurant>
) {
}