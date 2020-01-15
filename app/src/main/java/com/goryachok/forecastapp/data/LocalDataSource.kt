package com.goryachok.forecastapp.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.pojo.ForecastEntity
import com.goryachok.forecastapp.pojo.WeatherEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(context: Context) {
    init {
        Log.d("DaggerDebug", javaClass.name)
    }

    private val PREFS_NAME = "WEATHER_PREFERENCES"
    private val LOCAL_WEATHER_PREFS_NAME = "LOCAL_WEATHER_DATA"
    private val LOCAL_FORECAST_PREFS_NAME = "LOCAL_FORECAST_DATA"

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveData(remoteEntity: RemoteEntity) {
        when (remoteEntity) {
            is WeatherEntity -> {
                val str = Gson().toJson(remoteEntity, WeatherEntity::class.java)
                prefs.edit().putString(LOCAL_WEATHER_PREFS_NAME, str).apply()
            }
            is ForecastEntity -> {
                val str = Gson().toJson(remoteEntity, ForecastEntity::class.java)
                prefs.edit().putString(LOCAL_FORECAST_PREFS_NAME, str).apply()
            }
        }
    }

    fun getWeatherData(): WeatherEntity {
        val string = prefs.getString(LOCAL_WEATHER_PREFS_NAME, "")
        return Gson().fromJson(string, WeatherEntity::class.java)
    }

    fun getForecastData(): ForecastEntity {
        val string = prefs.getString(LOCAL_FORECAST_PREFS_NAME, "")
        return Gson().fromJson(string, ForecastEntity::class.java)
    }

    fun isDataAvailable(): Boolean {
        return prefs.contains(LOCAL_WEATHER_PREFS_NAME) && prefs.contains(LOCAL_FORECAST_PREFS_NAME)
    }
}