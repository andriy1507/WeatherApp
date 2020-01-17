package com.goryachok.forecastapp.di.components

import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.base.App
import com.goryachok.forecastapp.di.modules.*
import com.goryachok.forecastapp.view.activities.SplashActivity
import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment
import dagger.BindsInstance
import dagger.Component

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

    fun inject(fragment: CurrentWeatherFragment)

    fun inject(fragment: DailyForecastFragment)

    fun inject(fragment: HourlyForecastFragment)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(impl: WeatherApplication): Builder
    }
}