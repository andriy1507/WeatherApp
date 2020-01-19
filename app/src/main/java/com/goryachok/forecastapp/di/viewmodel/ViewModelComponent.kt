package com.goryachok.forecastapp.di.viewmodel

import com.goryachok.forecastapp.di.api.ApiModule
import com.goryachok.forecastapp.di.application.ApplicationModule
import com.goryachok.forecastapp.di.repository.RepositoryModule
import com.goryachok.forecastapp.view.activity.MainActivity
import com.goryachok.forecastapp.view.activity.SplashActivity
import com.goryachok.forecastapp.view.fragment.CurrentFragment
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

    fun inject(fragment: CurrentFragment)

    @Component.Builder
    interface Builder {

        fun build(): ViewModelComponent
    }
}