package com.goryachok.local.di

import com.goryachok.core.di.ApplicationProvider
import com.goryachok.local.LocalDataSource
import dagger.Component
import javax.inject.Singleton

interface LocalDataSourceProvider {

    fun provideLocalDataSource(): LocalDataSource
}

@Singleton
@Component(modules = [LocalDataSourceModule::class], dependencies = [ApplicationProvider::class])
interface LocalDataSourceComponent : LocalDataSourceProvider {

    @Component.Builder
    interface Builder {

        fun provider(provider: ApplicationProvider): Builder

        fun build(): LocalDataSourceComponent
    }
}