package com.goryachok.forecastapp.di.repository

import android.content.SharedPreferences
import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.LocalDataSourceImpl
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.data.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides

@Module(
    includes = [ApiModule::class,
        SharedPreferencesModule::class]
)
class DataSourceModule {

    @Provides
    fun provideRemoteDataSource(api: WeatherApiService): RemoteDataSource {
        return RemoteDataSourceImpl(api)
    }

    @Provides
    fun provideLocalDataSource(prefs: SharedPreferences): LocalDataSource {
        return LocalDataSourceImpl(prefs)
    }

}