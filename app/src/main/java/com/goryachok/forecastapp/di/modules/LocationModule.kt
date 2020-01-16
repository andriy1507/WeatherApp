package com.goryachok.forecastapp.di.modules

import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    fun provideLocationManager(context: Context): LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
}