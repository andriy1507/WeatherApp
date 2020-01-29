package com.goryachok.forecastapp.di.util

import com.goryachok.forecastapp.LocationProvider
import com.goryachok.forecastapp.WeatherApp
import dagger.Module
import dagger.Provides

@Module
class LocationProviderModule {

    @Provides
    fun provideLocationProvider(app: WeatherApp): LocationProvider {
        return LocationProvider(app)
    }
}