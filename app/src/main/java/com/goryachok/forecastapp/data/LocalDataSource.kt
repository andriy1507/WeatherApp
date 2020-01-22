package com.goryachok.forecastapp.data

import android.content.Context
import android.content.SharedPreferences
import com.goryachok.forecastapp.base.forecastFromJson
import com.goryachok.forecastapp.base.weatherFromJson
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity

class LocalDataSource(context: Context) {

    companion object {

        private const val PREF_NAME = "WEATHER_APPLICATION_PREFERENCES"
        private const val WEATHER_PREF = "LOCAL_WEATHER_PREFERENCES"
        private const val FORECAST_PREF = "LOCAL_FORECAST_PREFERENCES"
    }

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun saveForecastData(data: ForecastEntity) {
        preferences.edit().putString(FORECAST_PREF, data.toJson()).apply()
    }

    fun saveWeatherData(data: WeatherEntity) {
        preferences.edit().putString(WEATHER_PREF, data.toJson()).apply()
    }

    fun readForecastData(): ForecastEntity {
        val string = preferences.getString(FORECAST_PREF, "")
        return string.orEmpty().forecastFromJson()
    }

    fun readWeatherData(): WeatherEntity {
        val string = preferences.getString(WEATHER_PREF, "")
        return string.orEmpty().weatherFromJson()
    }

    fun isDataAvailable(): Boolean {
        return preferences.contains(FORECAST_PREF) && preferences.contains(WEATHER_PREF)
    }
}
