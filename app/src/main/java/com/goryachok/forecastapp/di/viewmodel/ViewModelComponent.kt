package com.goryachok.forecastapp.di.viewmodel

import com.goryachok.forecastapp.view.SplashActivity
import dagger.Component

@Component(modules = [ViewModelsModule::class])
interface ViewModelComponent{

    fun inject(activity:SplashActivity)

    @Component.Builder
    interface Builder{
        fun build():ViewModelComponent
    }
}