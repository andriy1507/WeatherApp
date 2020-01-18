package com.goryachok.forecastapp.di.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.data.RemoteDataSource
import com.goryachok.forecastapp.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideRemoteDataSource():RemoteDataSource{
        return RemoteDataSource()
    }

    @Provides
    fun provideLocalDataSource():LocalDataSource{
        return LocalDataSource()
    }

    @Provides
    fun provideRepository():Repository{
        return Repository()
    }
}