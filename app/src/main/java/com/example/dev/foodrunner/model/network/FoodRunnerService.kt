package com.example.dev.foodrunner.model.network

import androidx.lifecycle.LiveData
import com.example.dev.foodrunner.model.Result
import com.example.dev.foodrunner.model.network.pojo.RestaurantList
import com.example.dev.foodrunner.model.network.pojo.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("restaurants/fetch_result")
    suspend fun getRestaurants(): LiveData<List<RestaurantList>>

    @POST("login/fetch_result")
    suspend fun getUser(@Body user: User): User
}