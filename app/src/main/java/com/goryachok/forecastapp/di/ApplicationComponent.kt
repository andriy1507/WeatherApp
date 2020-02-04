package com.goryachok.forecastapp.di

import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core.di.RepositoryProvider
import com.goryachok.core.di.UtilsProvider
import com.goryachok.core_util.di.UtilsComponent
import com.goryachok.forecastapp.WeatherApp
import com.goryachok.repository.di.RepositoryComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class],
    dependencies = [RepositoryProvider::class, UtilsProvider::class]
)
interface ApplicationComponent : ApplicationProvider {

    @Component.Builder
    interface Builder {

        fun component(component: UtilsProvider): Builder

        fun component(component: RepositoryProvider): Builder

        @BindsInstance
        fun application(app: WeatherApp): Builder

        fun build(): ApplicationComponent
    }

    class Initializer {
        fun init(app: WeatherApp): ApplicationComponent {
            return DaggerApplicationComponent
                .builder()
                .component(UtilsComponent.Initializer().init(app))
                .component(RepositoryComponent.Initializer().init(app))
                .application(app)
                .build()
        }
    }
}