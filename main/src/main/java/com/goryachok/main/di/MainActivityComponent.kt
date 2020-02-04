package com.goryachok.main.di

import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core_ui.scope.ActivityScope
import com.goryachok.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationProvider::class]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationProvider): Builder

        @BindsInstance
        fun activity(activity: MainActivity): Builder

        fun build(): MainActivityComponent
    }

    class Initializer {

        fun init(activity: MainActivity): MainActivityComponent =
            DaggerMainActivityComponent.builder()
                .component((activity.applicationContext as App).component)
                .activity(activity)
                .build()
    }
}