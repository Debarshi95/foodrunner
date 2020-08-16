package com.example.dev.foodrunner

import android.app.Application
import com.example.dev.foodrunner.di.app_scope.AppComponent
import com.example.dev.foodrunner.di.app_scope.AppModule
import com.example.dev.foodrunner.di.app_scope.DaggerAppComponent
import com.example.dev.foodrunner.di.app_scope.NetworkModule


class FoodRunnerApplication : Application() {

    private lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }


    fun getAppComponent(): AppComponent = this.appComponent
}