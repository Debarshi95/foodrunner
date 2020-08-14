package com.example.dev.foodrunner.di.app_scope

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun providesNetworkModule() = NetworkModule()
}