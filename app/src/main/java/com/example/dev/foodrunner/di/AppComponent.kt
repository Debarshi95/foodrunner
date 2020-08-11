package com.example.dev.foodrunner.di

import com.example.dev.foodrunner.ui.MainActivity

interface AppComponent {
    fun inject(activity:MainActivity)

}