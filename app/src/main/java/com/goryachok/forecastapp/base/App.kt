package com.goryachok.forecastapp.base

import android.app.Application
import android.content.Context
import com.goryachok.forecastapp.di.components.AppComponent

abstract class App : Application() {
    abstract val context: Context
    abstract val component:AppComponent
}