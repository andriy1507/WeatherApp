package com.goryachok.forecastapp.base

import com.goryachok.forecastapp.di.components.AppComponent
import dagger.android.support.DaggerApplication

abstract class App : DaggerApplication() {
    abstract val component: AppComponent
}