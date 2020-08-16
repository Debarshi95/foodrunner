package com.example.dev.foodrunner.di.activity_scope

import androidx.lifecycle.ViewModel
import com.example.dev.foodrunner.ui.sign_in.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    @ActivityScope
    abstract fun bindSignInViewModel(signInViewModel: SignInViewModel): ViewModel
}