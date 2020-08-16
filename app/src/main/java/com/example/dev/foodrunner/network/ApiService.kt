package com.example.dev.foodrunner.network

import androidx.lifecycle.LiveData
import com.example.dev.foodrunner.model.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("restaurants/fetch_result")
    suspend fun getRestaurants(): LiveData<List<RestaurantList>>

    @POST("login/fetch_result")
    suspend fun getUser(@Body user: User): Result<User>
}