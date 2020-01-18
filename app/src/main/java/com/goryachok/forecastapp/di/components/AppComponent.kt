package com.goryachok.forecastapp.di.components

import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.base.App
import com.goryachok.forecastapp.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector

@Component(
    modules =
    [ActivityBuilderModule::class,
        ApiModule::class,
        AppModule::class,
        DataSourceModule::class,
        FragmentBuilderModule::class,
        LocationModule::class,
        PagerAdapterModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface AppComponent :AndroidInjector<WeatherApplication>{

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}