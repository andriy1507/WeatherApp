package com.goryachok.forecastapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.ui.activities.SplashActivity
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Module
    companion object {
        @Provides
        fun provideViewModel(
            activity: SplashActivity,
            factory: SplashViewModel.Factory
        ): SplashViewModel {
            return ViewModelProvider(activity, factory).get(SplashViewModel::class.java)
        }
    }

    @Binds
    abstract fun bindSplashViewModelFactory(impl: SplashViewModel.Factory): ViewModelProvider.Factory

//    @Binds
//    abstract fun bindHourlyViewModelFactory(impl: HourlyForecastViewModel.Factory): ViewModelProvider.Factory
//
//    @Binds
//    abstract fun bindCurrentViewModelFactory(impl: CurrentWeatherViewModel.Factory): ViewModelProvider.Factory
//
//    @Binds
//    abstract fun bindDailyViewModelFactory(impl: DailyForecastViewModel.Factory): ViewModelProvider.Factory
}