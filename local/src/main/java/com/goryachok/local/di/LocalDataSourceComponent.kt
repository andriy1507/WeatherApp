package com.goryachok.local.di

import com.goryachok.core.App
import com.goryachok.local.LocalDataSource
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface LocalDataSourceProvider {

    fun provideLocalDataSource(): LocalDataSource
}

@Singleton
@Component(modules = [LocalDataSourceModule::class])
interface LocalDataSourceComponent : LocalDataSourceProvider {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): LocalDataSourceComponent
    }

    class Initializer {
        fun init(app: App): LocalDataSourceComponent {
            return DaggerLocalDataSourceComponent.builder().app(app).build()
        }
    }
}