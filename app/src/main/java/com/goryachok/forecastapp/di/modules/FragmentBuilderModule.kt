package com.goryachok.forecastapp.di.modules

import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeCurrentFragment():CurrentWeatherFragment

    @ContributesAndroidInjector
    abstract fun contributeHourlyFragment():HourlyForecastFragment

    @ContributesAndroidInjector
    abstract fun contributeDailyFragment():DailyForecastFragment
}