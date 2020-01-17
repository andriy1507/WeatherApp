package com.goryachok.forecastapp.di.modules

import android.content.Context
import com.goryachok.forecastapp.api.OpenWeatherMapAPI
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun provideLocalDataSource(context:Context): LocalDataSource {
        return LocalDataSource(context)
    }

    @Provides
    fun provideRemoteDataSource(api:OpenWeatherMapAPI):RemoteDataSource{
        return RemoteDataSource(api)
    }
}