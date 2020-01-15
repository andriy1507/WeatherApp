package com.goryachok.forecastapp.base

import android.app.Application
import android.content.Context

abstract class App : Application() {
    abstract val context: Context
}