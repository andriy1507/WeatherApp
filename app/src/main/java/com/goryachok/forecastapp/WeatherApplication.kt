package com.goryachok.forecastapp


import android.content.Context
import android.util.Log
import com.goryachok.forecastapp.base.App
import com.goryachok.forecastapp.di.components.AppComponent
import com.goryachok.forecastapp.di.components.DaggerAppComponent
import javax.inject.Inject

class WeatherApplication : App() {

    override val component: AppComponent = DaggerAppComponent.builder().application(this).build()

    init {
        component.inject(this)
        Log.d("DaggerDebug", javaClass.name)
    }

    @Inject
    override lateinit var context: Context

}
