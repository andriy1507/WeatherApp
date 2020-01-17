package com.goryachok.forecastapp.di.components

import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.base.App
import com.goryachok.forecastapp.di.modules.*
import com.goryachok.forecastapp.view.activities.MainActivity
import com.goryachok.forecastapp.view.activities.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [ApiModule::class,
        AppModule::class,
        DataSourceModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        LocationModule::class]
)
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: SplashActivity)

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(impl: WeatherApplication): Builder
    }
}