package com.example.dev.foodrunner.model.pojo

import com.example.dev.foodrunner.model.pojo.Restaurant
import com.google.gson.annotations.SerializedName

data class RestaurantList(
    @SerializedName("data")
    val list: List<Restaurant>
) {
}