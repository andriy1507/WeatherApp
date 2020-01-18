package com.goryachok.forecastapp.di.api

import com.goryachok.forecastapp.data.RemoteDataSource
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(dataSource: RemoteDataSource)

    @Component.Builder
    interface Builder {
        fun build(): ApiComponent
    }
}