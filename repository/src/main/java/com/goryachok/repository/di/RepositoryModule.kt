package com.goryachok.repository.di

import com.goryachok.core.repository.ForecastRepository
import com.goryachok.core.repository.WeatherRepository
import com.goryachok.local.LocalDataSource
import com.goryachok.remote.RemoteDataSource
import com.goryachok.repository.ForecastRepositoryImpl
import com.goryachok.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideForecastRepository(
        local: LocalDataSource,
        remote: RemoteDataSource
    ): ForecastRepository = ForecastRepositoryImpl(local, remote)

    @Provides
    fun provideWeatherRepository(
        local: LocalDataSource,
        remote: RemoteDataSource
    ): WeatherRepository = WeatherRepositoryImpl(local, remote)
}