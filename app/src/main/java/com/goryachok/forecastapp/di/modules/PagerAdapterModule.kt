package com.goryachok.forecastapp.di.modules

import com.goryachok.forecastapp.view.activities.MainActivity
import com.goryachok.forecastapp.view.adapters.ForecastPagerAdapter
import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment
import dagger.Module
import dagger.Provides

@Module
class PagerAdapterModule {

    @Provides
    fun providePagerAdapter(
        activity: MainActivity,
        currentFragment: CurrentWeatherFragment,
        hourlyFragment: HourlyForecastFragment,
        dailyFragment: DailyForecastFragment
    ): ForecastPagerAdapter {
        val pages = listOf(currentFragment, hourlyFragment, dailyFragment)
        val manager = activity.supportFragmentManager
        return ForecastPagerAdapter(manager, pages)
    }
}