package com.goryachok.current.di

import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core_ui.scope.FragmentScope
import com.goryachok.current.CurrentFragment
import dagger.BindsInstance
import dagger.Component

@FragmentScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationProvider::class]
)
interface CurrentFragmentComponent {

    fun inject(fragment: CurrentFragment)

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationProvider): Builder

        @BindsInstance
        fun fragment(fragment: CurrentFragment): Builder

        fun build(): CurrentFragmentComponent
    }

    class Initializer {
        fun init(fragment: CurrentFragment): CurrentFragmentComponent {
            return DaggerCurrentFragmentComponent
                .builder()
                .component((fragment.requireActivity().applicationContext as App).component)
                .fragment(fragment)
                .build()
        }
    }
}