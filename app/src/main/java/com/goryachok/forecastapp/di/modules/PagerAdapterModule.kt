package com.goryachok.forecastapp.di.modules

import com.goryachok.forecastapp.base.PagerFragment
import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment
import dagger.Module
import dagger.Provides

@Module
class PagerAdapterModule {

    @Provides
    fun provideFragmentList(
        currentFragment: CurrentWeatherFragment,
        hourlyFragment: HourlyForecastFragment,
        dailyFragment: DailyForecastFragment
    ): List<PagerFragment> {
        return listOf(currentFragment, hourlyFragment, dailyFragment)
    }
}