package com.example.dev.foodrunner.model


import com.example.dev.foodrunner.model.pojo.BaseResponse
import com.example.dev.foodrunner.model.pojo.User
import com.example.dev.foodrunner.model.source.local.LocalDataSource
import com.example.dev.foodrunner.model.source.remote.RemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class MainRepository(
    private val remoteRemoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun userSignIn(user: User): ResponseState<BaseResponse<User>> {

        val signIn = remoteRemoteDataSource.userSignIn(user)
        if (signIn is ResponseState.Success) {
            localDataSource.setUserLoggedInStatus(true)
            return signIn
        }

        return ResponseState.Error(Exception("Some error occurred"))

    }

    fun getUserLoggedInState(): Boolean = localDataSource.getUserLoggedInState()
}