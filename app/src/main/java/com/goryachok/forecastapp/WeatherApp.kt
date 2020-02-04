package com.goryachok.forecastapp

import android.app.Application
import android.content.Context
import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.core_util.di.UtilsComponent
import com.goryachok.forecastapp.di.DaggerApplicationComponent
import com.goryachok.repository.di.RepositoryComponent
import timber.log.Timber

class WeatherApp : Application(), App {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun getContext(): Context = this

    override val component: ApplicationProvider by lazy {
        DaggerApplicationComponent
            .builder()
            .application(this)
            .component(RepositoryComponent.Initializer.init(this))
            .component(UtilsComponent.Initializer.init(this))
            .build()
    }
}