package com.goryachok.forecastapp

import android.app.Application
import android.content.Context
import com.goryachok.forecastapp.network.api.OpenWeatherMapAPI
import com.goryachok.forecastapp.network.connectivity.ConnectivityInterceptor
import com.goryachok.forecastapp.network.data.PREFS_NAME
import com.goryachok.forecastapp.network.data.WeatherDataRepository

class WeatherApplication : Application() {

    companion object {
        lateinit var repository: WeatherDataRepository
    }

    override fun onCreate() {
        super.onCreate()
        repository =
            WeatherDataRepository(
                OpenWeatherMapAPI(ConnectivityInterceptor(this)), getSharedPreferences(
                    PREFS_NAME,
                    Context.MODE_PRIVATE
                )
            )
    }
}

