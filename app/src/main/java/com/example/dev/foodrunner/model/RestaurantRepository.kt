package com.example.dev.foodrunner.model

import com.example.dev.foodrunner.network.ApiService
import com.example.dev.foodrunner.network.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception


class RestaurantRepository(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {


    suspend fun getUser(user: User): Result<Any> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(apiService.getUser(user))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}