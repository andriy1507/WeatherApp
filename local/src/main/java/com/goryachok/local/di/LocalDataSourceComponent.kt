package com.goryachok.local.di

import com.goryachok.core.LocalDataSourceProvider
import com.goryachok.local.LocalDataSource
import dagger.Component

@Component
interface LocalDataSourceComponent: LocalDataSourceProvider {

    fun inject(source: LocalDataSource)

    @Component.Builder
    interface Builder {

        fun build(): LocalDataSourceComponent
    }
}