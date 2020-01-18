package com.goryachok.forecastapp.di.application

import android.content.Context
import android.location.LocationManager
import com.goryachok.forecastapp.WeatherApp
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApp(): WeatherApp {
        return WeatherApp()
    }

    @Provides
    fun provideContext(app: WeatherApp): Context {
        return app
    }

    @Provides
    fun provideLocationManager(context: Context): LocationManager {
        return (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
    }

}
