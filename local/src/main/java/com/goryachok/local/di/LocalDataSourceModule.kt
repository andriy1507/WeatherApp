package com.goryachok.local.di

import com.goryachok.local.LocalDataSource
import com.goryachok.local.LocalDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class LocalDataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}