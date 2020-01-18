package com.goryachok.forecastapp.di.application

import android.app.Application
import com.goryachok.forecastapp.WeatherApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(app: Application)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: WeatherApp): Builder

        fun build(): ApplicationComponent
    }
}