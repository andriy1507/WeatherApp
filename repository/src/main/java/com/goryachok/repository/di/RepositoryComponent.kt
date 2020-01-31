package com.goryachok.repository.di

import com.goryachok.core.LocalDataSourceProvider
import com.goryachok.core.RemoteDataSourceProvider
import com.goryachok.core.RepositoryProvider
import com.goryachok.repository.ForecastRepository
import com.goryachok.repository.WeatherRepository
import dagger.Component

@Component(dependencies = [LocalDataSourceProvider::class, RemoteDataSourceProvider::class])
interface RepositoryComponent:RepositoryProvider {

    fun inject(repo: ForecastRepository)

    fun inject(repo: WeatherRepository)

    @Component.Builder
    interface Builder {

        fun localComponent(component: LocalDataSourceProvider):Builder

        fun remoteComponent(component: RemoteDataSourceProvider):Builder

        fun build(): RepositoryComponent
    }
}