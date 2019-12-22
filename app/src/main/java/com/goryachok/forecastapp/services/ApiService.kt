package com.goryachok.forecastapp.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.IBinder
import com.goryachok.forecastapp.services.GeolocationListener.Companion.geoLocation
import com.google.gson.Gson
import com.goryachok.forecastapp.api.ForecastController
import com.goryachok.forecastapp.api.WeatherController
import com.goryachok.forecastapp.model.WeatherResponse

class ApiService : Service() {

    companion object {
        const val preferencesName = "WeatherData"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val weatherPrefs = "Weather"
        const val forecastPrefs = "Forecast"
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val preferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val weather = WeatherController(preferences)
        val forecast = ForecastController(preferences)

        if (intent?.hasExtra("City")!!) {
            weather.start(intent.getStringExtra("City"))
            forecast.start(intent.getStringExtra("City"))
        } else {
            if (!checkLocalData()) {
                val latitude = geoLocation.latitude.toFloat()
                val longitude = geoLocation.longitude.toFloat()
                weather.startWithCoord(latitude,longitude)
                forecast.startWithCoord(latitude, longitude)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun checkLocalData(): Boolean {
        val preferences = getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        if (preferences.contains(weatherPrefs) && preferences.contains(forecastPrefs)) {
            val json = preferences.getString(weatherPrefs, "")
            val weather = Gson().fromJson(json, WeatherResponse::class.java)
            val age = System.currentTimeMillis() - weather.dt
            if (age < 3600)
                return true
        }
        return false
    }
}