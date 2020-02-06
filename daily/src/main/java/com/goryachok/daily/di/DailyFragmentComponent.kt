package com.goryachok.daily.di

import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core_ui.scope.FragmentScope
import com.goryachok.daily.DailyFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationProvider::class]
)
@FragmentScope
interface DailyFragmentComponent {

    fun inject(fragment: DailyFragment)

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationProvider): Builder

        @BindsInstance
        fun fragment(fragment: DailyFragment): Builder

        fun build(): DailyFragmentComponent
    }

    class Initializer {
        fun init(fragment: DailyFragment): DailyFragmentComponent {
            return DaggerDailyFragmentComponent
                .builder()
                .component((fragment.requireActivity().applicationContext as App).component)
                .fragment(fragment)
                .build()
        }
    }
}