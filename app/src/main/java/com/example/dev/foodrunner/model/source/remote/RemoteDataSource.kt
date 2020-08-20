package com.example.dev.foodrunner.model.source.remote

import com.example.dev.foodrunner.model.ResponseState
import com.example.dev.foodrunner.model.pojo.BaseResponse
import com.example.dev.foodrunner.model.pojo.User
import retrofit2.Response

interface RemoteDataSource {

    suspend fun userSignIn(user: User): ResponseState<BaseResponse<User>>


}