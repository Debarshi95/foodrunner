package com.example.dev.foodrunner.di.activity_scope

import androidx.lifecycle.ViewModelProvider
import com.example.dev.foodrunner.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {


    @Binds
    @ActivityScope
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}