package com.goryachok.splash.di

import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core_ui.scope.ActivityScope
import com.goryachok.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [ViewModelModule::class],
    dependencies = [ApplicationProvider::class]
)
@ActivityScope
interface SplashActivityComponent {

    fun inject(activity: SplashActivity)

    @Component.Builder
    interface Builder {

        fun component(component: ApplicationProvider): Builder

        @BindsInstance
        fun activity(activity: SplashActivity): Builder

        fun build(): SplashActivityComponent
    }

    class Initializer {
        fun init(activity: SplashActivity): SplashActivityComponent {
            return DaggerSplashActivityComponent.builder()
                .activity(activity)
                .component((activity.applicationContext as App).component)
                .build()
        }
    }
}