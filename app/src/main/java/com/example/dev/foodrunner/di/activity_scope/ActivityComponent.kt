package com.example.dev.foodrunner.di.activity_scope

import com.example.dev.foodrunner.di.app_scope.AppComponent
import com.example.dev.foodrunner.ui.sign_in.SignInFragment
import com.example.dev.foodrunner.ui.sign_up.SignUpFragment
import dagger.Component

@ActivityScope
@Component(modules = [ViewModelFactoryModule::class,ViewModelModule::class],dependencies = [AppComponent::class])
interface ActivityComponent {
    fun inject(signInFragment: SignInFragment)
    fun inject(signUpFragment: SignUpFragment)

}