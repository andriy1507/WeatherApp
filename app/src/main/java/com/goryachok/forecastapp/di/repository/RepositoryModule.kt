package com.goryachok.forecastapp.di.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.BaseRepository
import com.goryachok.forecastapp.repository.ForecastRepository
import com.goryachok.forecastapp.repository.WeatherRepository
import dagger.Module
import dagger.Provides

@Module(includes = [DataSourceModule::class])
class RepositoryModule {

    @Provides
    fun provideWeatherRepository(
        remote: RemoteDataSource,
        local: LocalDataSource
    ): BaseRepository<WeatherEntity> {
        return WeatherRepository(remote, local)
    }

    @Provides
    fun provideForecastRepository(
        remote: RemoteDataSource,
        local: LocalDataSource
    ): BaseRepository<ForecastEntity> {
        return ForecastRepository(remote, local)
    }
}