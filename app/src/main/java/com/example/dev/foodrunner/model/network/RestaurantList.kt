package com.example.dev.foodrunner.model.network

import com.google.gson.annotations.SerializedName

data class RestaurantList(
    @SerializedName("data")
    val list: List<Restaurant>
) {
}