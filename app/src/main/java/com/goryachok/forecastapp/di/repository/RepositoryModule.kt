package com.goryachok.forecastapp.di.repository

import android.content.Context
import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteDataSource(api: WeatherApiService): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    fun provideLocalDataSource(context: Context): LocalDataSource {
        return LocalDataSource(context)
    }

    @Provides
    fun provideRepository(remote: RemoteDataSource, local: LocalDataSource): Repository {
        return Repository(remote, local)
    }
}