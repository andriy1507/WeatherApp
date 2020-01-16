package com.goryachok.forecastapp.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.goryachok.forecastapp.base.RemoteEntity
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import javax.inject.Inject
import kotlin.reflect.KClass

class LocalDataSource @Inject constructor(context: Context) {

    companion object{
        private const val DAGGER_TAG = "DaggerDebug"
    }
    init {
        Log.d(DAGGER_TAG, javaClass.name)
    }

    private val prefsName = "WEATHER_PREFERENCES"
    private val weatherPrefs = "LOCAL_WEATHER_DATA"
    private val forecastPrefs = "LOCAL_FORECAST_DATA"

    private val prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    fun saveData(remoteEntity: RemoteEntity) {
        when (remoteEntity) {
            is WeatherEntity -> {
                val str = Gson().toJson(remoteEntity, WeatherEntity::class.java)
                prefs.edit().putString(weatherPrefs, str).apply()
            }
            is ForecastEntity -> {
                val str = Gson().toJson(remoteEntity, ForecastEntity::class.java)
                prefs.edit().putString(forecastPrefs, str).apply()
            }
        }
    }

    fun lastFetchTime() = if (isDataAvailable()) getData(WeatherEntity::class).date else 0

//    fun getWeatherData(): WeatherEntity {
//        val string = prefs.getString(weatherPrefs, "")
//        return Gson().fromJson(string, WeatherEntity::class.java)
//    }
//
//    fun getForecastData(): ForecastEntity {
//        val string = prefs.getString(forecastPrefs, "")
//        return Gson().fromJson(string, ForecastEntity::class.java)
//    }

    @Suppress("UNCHECKED_CAST")
    fun <T : RemoteEntity> getData(type: KClass<T>): T {
        return when (type) {
            WeatherEntity::class -> Gson().fromJson(
                prefs.getString(weatherPrefs, ""),
                WeatherEntity::class.java
            ) as T
            ForecastEntity::class -> Gson().fromJson(
                prefs.getString(forecastPrefs, ""),
                ForecastEntity::class.java
            ) as T
            else -> throw ClassCastException()
        }
    }

    fun isDataAvailable(): Boolean {
        return prefs.contains(weatherPrefs) && prefs.contains(forecastPrefs)
    }
}