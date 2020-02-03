package com.goryachok.forecastapp.di

import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core.di.RepositoryProvider
import com.goryachok.core.di.ViewModelsProvider
import com.goryachok.forecastapp.WeatherApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class],
    dependencies = [RepositoryProvider::class, ViewModelsProvider::class]
)
interface ApplicationComponent : ApplicationProvider {

    @Component.Builder
    interface Builder {

        fun component(component: ViewModelsProvider)

        fun component(component: RepositoryProvider)

        @BindsInstance
        fun application(app: WeatherApp): Builder

        fun build(): ApplicationComponent
    }
}