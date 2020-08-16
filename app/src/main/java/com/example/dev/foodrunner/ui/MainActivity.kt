package com.example.dev.foodrunner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dev.foodrunner.FoodRunnerApplication
import com.example.dev.foodrunner.R
import com.example.dev.foodrunner.di.activity_scope.ActivityComponent
import com.example.dev.foodrunner.di.activity_scope.DaggerActivityComponent
import com.example.dev.foodrunner.di.app_scope.NetworkModule
import javax.inject.Inject
import javax.inject.Provider

typealias ActivityComponentProvider = Provider<ActivityComponent>

class MainActivity : AppCompatActivity(), ActivityComponentProvider {
    private lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        val app = (application as FoodRunnerApplication).getAppComponent()
        app.inject(this)
        activityComponent = DaggerActivityComponent.builder().appComponent(app).build()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun get(): ActivityComponent {
        return activityComponent
    }


}