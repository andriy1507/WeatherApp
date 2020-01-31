package com.goryachok.remote.di

import com.goryachok.core.RemoteDataSourceProvider
import com.goryachok.remote.RemoteDataSource
import dagger.Component

@Component
interface RemoteDataSourceComponent:RemoteDataSourceProvider {

    fun inject(source: RemoteDataSource)

    @Component.Builder
    interface Builder {

        fun build(): RemoteDataSourceComponent
    }
}