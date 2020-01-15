package com.goryachok.forecastapp.data

import android.content.Context
import com.google.gson.Gson
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.pojo.ForecastResponse
import com.goryachok.forecastapp.pojo.WeatherResponse

class LocalDataSource(context: Context) {
    private val PREFS_NAME = "WEATHER_PREFERENCES"
    private val LOCAL_WEATHER_PREFS_NAME = "LOCAL_WEATHER_DATA"
    private val LOCAL_FORECAST_PREFS_NAME = "LOCAL_FORECAST_DATA"

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveData(remoteEntity: RemoteEntity) {
        when (remoteEntity) {
            is WeatherResponse -> {
                val str = Gson().toJson(remoteEntity, WeatherResponse::class.java)
                prefs.edit().putString(LOCAL_WEATHER_PREFS_NAME, str).apply()
            }
            is ForecastResponse -> {
                val str = Gson().toJson(remoteEntity, ForecastResponse::class.java)
                prefs.edit().putString(LOCAL_FORECAST_PREFS_NAME, str).apply()
            }
        }
    }

    fun getWeatherData():WeatherResponse{
        val string = prefs.getString(LOCAL_WEATHER_PREFS_NAME, "")
        return Gson().fromJson(string, WeatherResponse::class.java)
    }

    fun getForecastData():ForecastResponse{
        val string = prefs.getString(LOCAL_FORECAST_PREFS_NAME, "")
        return Gson().fromJson(string, ForecastResponse::class.java)
    }
}