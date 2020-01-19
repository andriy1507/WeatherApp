package com.goryachok.forecastapp.di.viewmodel

import com.goryachok.forecastapp.di.api.ApiModule
import com.goryachok.forecastapp.di.application.ApplicationModule
import com.goryachok.forecastapp.di.repository.RepositoryModule
import com.goryachok.forecastapp.view.activity.MainActivity
import com.goryachok.forecastapp.view.activity.SplashActivity
import dagger.Component

@Component(
    modules = [ViewModelsModule::class,
    ApplicationModule::class,
    RepositoryModule::class,
    ApiModule::class]
)
interface ViewModelComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build(): ViewModelComponent
    }
}