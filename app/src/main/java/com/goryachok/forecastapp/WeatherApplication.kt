package com.goryachok.forecastapp


import android.util.Log
import com.goryachok.forecastapp.base.App
import com.goryachok.forecastapp.di.components.AppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class WeatherApplication : App() {

    companion object{
        private const val DAGGER_TAG = "DaggerDebug"
    }

    init {
        Log.d(DAGGER_TAG, javaClass.name)
    }

    override val component: AppComponent
        get() = applicationInjector() as AppComponent

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
