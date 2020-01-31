package com.goryachok.core

import android.app.Activity
import com.goryachok.core.base.*

interface ApplicationProvider : RepositoryProvider {

    fun inject(activity: Activity)

    fun provideApp(): App
}

interface RepositoryProvider {

    fun provideForecastRepository(): ForecastRepository

    fun provideWeatherRepository(): WeatherRepository
}

interface UtilsProvider {

    fun provideConnectivityListener(): ConnectivityListener

    fun provideLocationProvider(): LocationProvider
}
