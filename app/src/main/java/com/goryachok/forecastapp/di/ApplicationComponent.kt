package com.goryachok.forecastapp.di

import com.goryachok.core.ApplicationProvider
import com.goryachok.core.RepositoryProvider
import com.goryachok.core.UtilsProvider
import com.goryachok.forecastapp.WeatherApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, UtilsModule::class],
    dependencies = [RepositoryProvider::class, UtilsProvider::class]
)
interface ApplicationComponent : ApplicationProvider {

    @Component.Builder
    interface Builder {

        fun component(component: UtilsProvider)

        fun component(component: RepositoryProvider)

        @BindsInstance
        fun application(app: WeatherApp): Builder

        fun build(): ApplicationComponent
    }
}