package com.goryachok.forecastapp.di.modules

import android.content.Context
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.base.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context {
        return app
    }
    @Provides
    fun provideApp(impl:WeatherApplication):App{
        return impl
    }
}