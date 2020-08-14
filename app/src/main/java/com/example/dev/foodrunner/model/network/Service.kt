package com.example.dev.foodrunner.model.network

import androidx.lifecycle.LiveData
import retrofit2.http.GET

interface Service {
    @GET("/restaurants/fetch_result")
    suspend fun getRestaurants(): LiveData<List<RestaurantList>>
}