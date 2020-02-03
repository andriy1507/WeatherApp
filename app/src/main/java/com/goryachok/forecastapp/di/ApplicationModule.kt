package com.goryachok.forecastapp.di

import com.goryachok.core.App
import com.goryachok.forecastapp.WeatherApp
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

    @Provides
    fun provideApp(app: WeatherApp): App = app
}