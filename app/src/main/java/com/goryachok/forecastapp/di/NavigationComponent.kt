package com.goryachok.forecastapp.di

import com.goryachok.core.di.NavigationProvider
import dagger.Component

@Component(modules = [NavigationModule::class])
interface NavigationComponent : NavigationProvider {

    @Component.Builder
    interface Builder {

        fun build(): NavigationComponent
    }

    class Initializer {
        fun init(): NavigationComponent = DaggerNavigationComponent.create()
    }
}