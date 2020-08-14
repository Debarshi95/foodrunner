package com.example.dev.foodrunner.di.app_scope

import com.example.dev.foodrunner.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun appModule(appModule: AppModule)
    fun netWorkModule(): NetworkModule
}