package com.goryachok.forecastapp.dagger.splashViewModel

import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.activities.SplashActivity
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Module
    companion object{
        @Provides
        fun provideViewModel(activity:SplashActivity, factory:SplashViewModel.Factory): SplashViewModel {
            return ViewModelProvider(activity,factory).get(SplashViewModel::class.java)
        }
    }

    @Binds
    abstract fun bindViewModelFactory(impl: SplashViewModel.Factory): ViewModelProvider.Factory
}