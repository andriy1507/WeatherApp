package com.goryachok.forecastapp

import android.app.Application
import com.goryachok.forecastapp.di.application.DaggerApplicationComponent

class WeatherApp : Application() {

    init {
        DaggerApplicationComponent.builder().application(this).build()
    }
}