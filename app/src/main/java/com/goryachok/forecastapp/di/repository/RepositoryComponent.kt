package com.goryachok.forecastapp.di.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.di.api.ApiModule
import com.goryachok.forecastapp.di.application.ApplicationModule
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.viewmodel.MainViewModel
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.Component

@Component(
    modules = [RepositoryModule::class,
    ApplicationModule::class,
    ApiModule::class]
)
interface RepositoryComponent {

    fun inject(viewModel: SplashViewModel)

    fun inject(viewModel: MainViewModel)

    fun inject(repository: Repository)

    fun inject(local: LocalDataSource)

    @Component.Builder
    interface Builder {

        fun build(): RepositoryComponent
    }
}