package com.goryachok.forecastapp.di.modules

import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.view.activities.SplashActivity
import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment
import com.goryachok.forecastapp.viewmodel.CurrentWeatherViewModel
import com.goryachok.forecastapp.viewmodel.DailyForecastViewModel
import com.goryachok.forecastapp.viewmodel.HourlyForecastViewModel
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Module
    companion object {
        @Provides
        fun provideSplashViewModel(
            activity: SplashActivity,
            factory: SplashViewModel.Factory
        ): SplashViewModel {
            return ViewModelProvider(activity, factory).get(SplashViewModel::class.java)
        }
        @Provides
        fun provideCurrentViewModel(
            activity: CurrentWeatherFragment,
            factory: CurrentWeatherViewModel.Factory
        ): CurrentWeatherViewModel {
            return ViewModelProvider(activity, factory).get(CurrentWeatherViewModel::class.java)
        }

        @Provides
        fun provideHourlyViewModel(
            activity: HourlyForecastFragment,
            factory: HourlyForecastViewModel.Factory
        ): HourlyForecastViewModel {
            return ViewModelProvider(activity, factory).get(HourlyForecastViewModel::class.java)
        }
        @Provides
        fun provideDailyViewModel(
            activity: DailyForecastFragment,
            factory: DailyForecastViewModel.Factory
        ): DailyForecastViewModel {
            return ViewModelProvider(activity, factory).get(DailyForecastViewModel::class.java)
        }
    }
}