package com.example.dev.foodrunner.model.source.remote


import android.util.Log
import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.ResponseState.Success
import com.example.dev.foodrunner.model.network.FoodRunnerService
import com.example.dev.foodrunner.model.pojo.BaseResponse
import com.example.dev.foodrunner.model.pojo.Data
import com.example.dev.foodrunner.model.pojo.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.Exception

class RemoteRemoteDataSourceImpl(
    private val foodRunnerService: FoodRunnerService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteDataSource {


    override suspend fun userSignIn(user: User): ResponseState<BaseResponse<User>> =
        withContext(ioDispatcher) {
            try {
                val res = foodRunnerService.userSignIn(user)
                if (res.isSuccessful && res.body() != null) {
                    return@withContext Success(res.body()!!)
                } else if (!res.isSuccessful) {
                    return@withContext ResponseState.Error(Exception(res.message()))
                } else {
                    return@withContext ResponseState.Error(Exception(res.errorBody().toString()))
                }
            } catch (e: Exception) {
                return@withContext ResponseState.Error(e)
            }

        }


}