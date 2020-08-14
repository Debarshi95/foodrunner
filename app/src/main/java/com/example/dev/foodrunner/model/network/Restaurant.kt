package com.example.dev.foodrunner.model.network

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: String,
    val name: String,
    val rating: String,
    @SerializedName("cost_for_one")
    val cost: String,
    @SerializedName("image_url")
    val imgUrl: String
) {
}