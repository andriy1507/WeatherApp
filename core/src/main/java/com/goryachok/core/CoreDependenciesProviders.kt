package com.goryachok.core

import android.app.Activity
import androidx.fragment.app.Fragment
import com.goryachok.core.base.App
import com.goryachok.core.base.ForecastRepository
import com.goryachok.core.base.WeatherRepository

interface ApplicationProvider : RepositoryProvider, UtilsProvider {

    fun inject(activity: Activity)

    fun inject(fragment: Fragment)

    fun provideApp(): App
}

interface RepositoryProvider {

    fun provideForecastRepository(): ForecastRepository

    fun provideWeatherRepository(): WeatherRepository
}

interface UtilsProvider