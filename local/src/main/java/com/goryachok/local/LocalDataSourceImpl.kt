package com.goryachok.local

import android.content.SharedPreferences
import com.goryachok.local.model.*

class LocalDataSourceImpl(override val preferences: SharedPreferences) : LocalDataSource {

    companion object {
        const val PREF_NAME = "WEATHER_APPLICATION_PREFERENCES"
        private const val WEATHER_PREF = "LOCAL_WEATHER_PREFERENCES"
        private const val FORECAST_PREF = "LOCAL_FORECAST_PREFERENCES"
    }

    override fun saveForecastData(data: LocalForecast) {
        preferences.edit().putString(FORECAST_PREF, data.toJson()).apply()
    }

    override fun saveWeatherData(data: LocalWeather) {
        preferences.edit().putString(WEATHER_PREF, data.toJson()).apply()
    }

    override fun readForecastData(): LocalForecast {
        val string = preferences.getString(FORECAST_PREF, "")
        return string.orEmpty().forecastFromJson()
    }

    override fun readWeatherData(): LocalWeather {
        val string = preferences.getString(WEATHER_PREF, "")
        return string.orEmpty().weatherFromJson()
    }

    override fun isDataAvailable(): Boolean {
        return preferences.contains(FORECAST_PREF) && preferences.contains(WEATHER_PREF)
    }
}