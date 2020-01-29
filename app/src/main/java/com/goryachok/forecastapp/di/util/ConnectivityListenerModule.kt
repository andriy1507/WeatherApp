package com.goryachok.forecastapp.di.util

import com.goryachok.forecastapp.ConnectivityListener
import com.goryachok.forecastapp.WeatherApp
import dagger.Module
import dagger.Provides

@Module
class ConnectivityListenerModule {

    @Provides
    fun provideConnectivityListener(app: WeatherApp): ConnectivityListener {
        return ConnectivityListener(app)
    }
}