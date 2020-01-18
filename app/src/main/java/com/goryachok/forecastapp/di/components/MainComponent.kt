package com.goryachok.forecastapp.di.components

import com.goryachok.forecastapp.di.modules.*
import com.goryachok.forecastapp.view.activities.MainActivity
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(
    dependencies = [AppComponent::class],
    modules = [AndroidInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class,
        ApiModule::class,
        DataSourceModule::class,
        LocationModule::class,
        PagerAdapterModule::class,
        RepositoryModule::class,
        ViewModelModule::class]
)
interface MainComponent : AndroidInjector<MainActivity> {

    @Component.Builder
    interface Builder {

        fun appComponent(component: AppComponent): Builder

        fun build(): AndroidInjector<MainActivity>
    }
}
