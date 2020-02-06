package com.goryachok.forecastapp

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.goryachok.core.App
import com.goryachok.core.di.ApplicationProvider
import com.goryachok.forecastapp.di.ApplicationComponent
import timber.log.Timber

class WeatherApp : MultiDexApplication(), App {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun getContext(): Context = this

    override val component: ApplicationProvider by lazy {
        ApplicationComponent.Initializer().init(this)
    }
}