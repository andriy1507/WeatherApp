package com.goryachok.forecastapp

import android.app.Application
import android.content.Context
import com.goryachok.core.base.App
import timber.log.Timber

class WeatherApp : Application(), App {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun getContext(): Context = this

}