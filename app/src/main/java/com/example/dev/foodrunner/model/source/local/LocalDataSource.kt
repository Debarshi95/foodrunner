package com.example.dev.foodrunner.model.source.local


interface LocalDataSource {

    fun setUserLoggedInStatus(status: Boolean)
    fun getUserLoggedInState(): Boolean
}