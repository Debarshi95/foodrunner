package com.example.dev.foodrunner.model.source.local

import android.content.SharedPreferences
import com.example.dev.foodrunner.model.pojo.User

class LocalDataSourceImpl(private val sharedPreferences: SharedPreferences) : LocalDataSource {


    override fun setUserLoggedInStatus(status: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(LOGGED_IN_STATUS, status)
        }.apply()
    }

    override fun getUserLoggedInState(): Boolean {
        return sharedPreferences.getBoolean(LOGGED_IN_STATUS, false)
    }

    companion object {
        const val LOGGED_IN_STATUS = "isLoggedIn"
    }
}