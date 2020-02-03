package com.goryachok.repository.di

import com.goryachok.core.di.RepositoryProvider
import com.goryachok.local.di.LocalDataSourceProvider
import com.goryachok.remote.di.RemoteDataSourceProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class],
    dependencies = [RemoteDataSourceProvider::class, LocalDataSourceProvider::class]
)
interface RepositoryComponent : RepositoryProvider {

    @Component.Builder
    interface Builder {

        fun component(component: LocalDataSourceProvider): Builder

        fun component(component: RemoteDataSourceProvider): Builder

        fun build(): RepositoryComponent
    }
}