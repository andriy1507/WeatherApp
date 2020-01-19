package com.goryachok.forecastapp.data

import android.content.Context
import android.content.SharedPreferences
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(val context: Context) {

    companion object {
        private const val PREF_NAME = "WEATHER_APPLICATION_PREFERENCES"
        private const val WEATHER_PREF = "LOCAL_WEATHER_PREFERENCES"
        private const val FORECAST_PREF = "LOCAL_FORECAST_PREFERENCES"
        private const val MODE = Context.MODE_PRIVATE
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREF_NAME,
            MODE
        )
    }

    init {
        DaggerRepositoryComponent.create().inject(this)
    }

    fun saveForecastData(data: ForecastEntity) {
        preferences.edit().putString(FORECAST_PREF, data.toJson()).apply()
    }

    fun saveWeatherData(data: WeatherEntity) {
        preferences.edit().putString(WEATHER_PREF, data.toJson()).apply()
    }

    fun readForecastData(): ForecastEntity {
        val string = preferences.getString(FORECAST_PREF, "")
        return ForecastEntity.fromGson(string ?: "")
    }

    fun readWeatherData(): WeatherEntity {
        val string = preferences.getString(WEATHER_PREF, "")
        return WeatherEntity.fromJson(string ?: "")
    }

    fun isDataAvailable(): Boolean {
        return preferences.contains(FORECAST_PREF) && preferences.contains(WEATHER_PREF)
    }
}
