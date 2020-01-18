package com.goryachok.forecastapp.di.repository

import com.goryachok.forecastapp.data.LocalDataSource
import com.goryachok.forecastapp.di.application.ApplicationModule
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import dagger.Component

@Component(modules = [RepositoryModule::class, ApplicationModule::class])
interface RepositoryComponent {

    fun inject(viewModel: SplashViewModel)

    fun inject(repository: Repository)

    fun inject(local: LocalDataSource)

    @Component.Builder
    interface Builder {

        fun build(): RepositoryComponent
    }
}