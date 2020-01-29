package com.goryachok.forecastapp.di.repository

import android.content.Context
import android.content.SharedPreferences
import com.goryachok.forecastapp.WeatherApp
import com.goryachok.forecastapp.base.PREF_NAME
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {

    @Provides
    fun provideSharedPreferences(app: WeatherApp): SharedPreferences {
        return app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
}