package com.goryachok.splash.di

import androidx.lifecycle.ViewModelProvider
import com.goryachok.splash.SplashActivity
import com.goryachok.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindSplashViewModelFactory(impl: SplashViewModel.Factory): ViewModelProvider.Factory

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideSplashViewModel(
            activity: SplashActivity,
            factory: ViewModelProvider.Factory
        ): SplashViewModel {
            return ViewModelProvider(activity, factory).get(SplashViewModel::class.java)
        }
    }
}