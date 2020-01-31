package com.goryachok.forecastapp.di

import android.app.Application
import com.goryachok.core.ApplicationProvider
import com.goryachok.core.RepositoryProvider
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [RepositoryProvider::class])
interface ApplicationComponent : ApplicationProvider {

    fun inject(app: Application)

    @Component.Builder
    interface Builder {

        fun repoComponent(component: RepositoryProvider)

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): ApplicationComponent
    }
}