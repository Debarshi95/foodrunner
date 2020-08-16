package com.example.dev.foodrunner.di.app_scope

import com.example.dev.foodrunner.model.RestaurantRepository
import com.example.dev.foodrunner.network.ApiService
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    companion object {
        @Provides
        fun providesRepository(apiService: ApiService): RestaurantRepository =
            RestaurantRepository(apiService = apiService)
    }

}