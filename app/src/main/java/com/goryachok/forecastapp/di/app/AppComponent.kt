package com.goryachok.forecastapp.di.app

import com.goryachok.forecastapp.WeatherApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    modules = [AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class]
)
interface AppComponent : AndroidInjector<WeatherApp> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<WeatherApp>
}