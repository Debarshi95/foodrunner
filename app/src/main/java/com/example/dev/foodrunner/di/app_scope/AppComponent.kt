package com.example.dev.foodrunner.di.app_scope

import com.example.dev.foodrunner.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    fun inject(activity: MainActivity)
}