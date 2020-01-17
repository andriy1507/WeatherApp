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

    companion object {
        private const val DAGGER_TAG = "DaggerDebug"

        private const val PREFS_NAME = "WEATHER_PREFERENCES"
        private const val WEATHER_PREFS = "LOCAL_WEATHER_DATA"
        private const val FORECAST_PREFS = "LOCAL_FORECAST_DATA"
    }

    init {
        Log.d(DAGGER_TAG, javaClass.name)
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveData(remoteEntity: RemoteEntity) {
        when (remoteEntity) {
            is WeatherEntity -> {
                prefs.edit().putString(WEATHER_PREFS, remoteEntity.toJson()).apply()
            }
            is ForecastEntity -> {
                prefs.edit().putString(FORECAST_PREFS, remoteEntity.toJson()).apply()
            }
        }
    }

    fun lastFetchTime() = if (isDataAvailable()) getData(WeatherEntity::class).date else 0

    @Suppress("UNCHECKED_CAST")
    fun <T : RemoteEntity> getData(type: KClass<T>): T =
        when (type) {
            WeatherEntity::class -> Gson().fromJson(
                prefs.getString(WEATHER_PREFS, ""),
                WeatherEntity::class.java
            ) as T
            ForecastEntity::class -> Gson().fromJson(
                prefs.getString(FORECAST_PREFS, ""),
                ForecastEntity::class.java
            ) as T
            else -> throw ClassCastException()
        }

    fun isDataAvailable() = prefs.contains(WEATHER_PREFS) && prefs.contains(FORECAST_PREFS)

}