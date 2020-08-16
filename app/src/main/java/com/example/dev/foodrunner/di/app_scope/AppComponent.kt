package com.example.dev.foodrunner.di.app_scope

import com.example.dev.foodrunner.di.activity_scope.ViewModelModule
import com.example.dev.foodrunner.model.RestaurantRepository
import com.example.dev.foodrunner.network.ApiService
import com.example.dev.foodrunner.ui.MainActivity
import com.example.dev.foodrunner.ui.sign_in.SignInViewModel
import com.example.dev.foodrunner.utils.ViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

//    fun getAPIService(): ApiService
    fun getRepo(): RestaurantRepository
//    fun getSignInViewModel():SignInViewModel


}