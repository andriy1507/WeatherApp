package com.goryachok.core.di

import com.goryachok.core.App
import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import com.goryachok.core.navigation.StartMainActivityAction
import com.goryachok.core.repository.ForecastRepository
import com.goryachok.core.repository.WeatherRepository

interface ApplicationProvider : RepositoryProvider, UtilsProvider, NavigationProvider {

    fun provideApp(): App
}

interface RepositoryProvider {

    fun provideForecastRepository(): ForecastRepository

    fun provideWeatherRepository(): WeatherRepository
}

interface UtilsProvider {

    fun provideLocationProvider(): LocationProvider

    fun provideConnectivityListener(): ConnectivityListener
}

interface NavigationProvider {

    fun provideStartMainActivityAction(): StartMainActivityAction
}
