package com.goryachok.forecastapp.di.application

import android.content.Context
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
}
