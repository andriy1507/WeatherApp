package com.goryachok.forecastapp.di.modules

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): Repository {
        return Repository(remoteDataSource,localDataSource)
    }
}