package com.goryachok.remote.di

import com.goryachok.remote.RemoteDataSource
import com.goryachok.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource
}