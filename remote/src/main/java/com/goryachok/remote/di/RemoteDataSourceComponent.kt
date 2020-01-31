package com.goryachok.remote.di

import com.goryachok.remote.RemoteDataSource
import dagger.Component
import javax.inject.Singleton

interface RemoteDataSourceProvider {

    fun provideRemoteDataSource(): RemoteDataSource
}

@Singleton
@Component(modules = [WeatherApiModule::class, RemoteDataSourceModule::class])
interface RemoteDataSourceComponent : RemoteDataSourceProvider {

    @Component.Builder
    interface Builder {

        fun build(): RemoteDataSourceComponent
    }
}