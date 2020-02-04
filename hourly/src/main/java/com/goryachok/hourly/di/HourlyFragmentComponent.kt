package com.goryachok.hourly.di

import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core_ui.scope.FragmentScope
import com.goryachok.hourly.HourlyFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationProvider::class]
)
@FragmentScope
interface HourlyFragmentComponent {

    fun inject(fragment: HourlyFragment)

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationProvider): Builder

        @BindsInstance
        fun fragment(fragment: HourlyFragment): Builder

        fun build(): HourlyFragmentComponent
    }

    class Initializer {

        fun init(fragment: HourlyFragment): HourlyFragmentComponent {
            return DaggerHourlyFragmentComponent
                .builder()
                .component((fragment.requireActivity().applicationContext as App).component)
                .fragment(fragment)
                .build()
        }
    }
}