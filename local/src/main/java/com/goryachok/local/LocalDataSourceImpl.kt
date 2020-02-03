package com.goryachok.local

import android.content.Context
import android.content.SharedPreferences
import com.goryachok.core.base.App
import com.goryachok.local.model.*
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(app: App) : LocalDataSource {

    private val preferences: SharedPreferences =
        app.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "WEATHER_APPLICATION_PREFERENCES"
        private const val WEATHER_PREF = "LOCAL_WEATHER_PREFERENCES"
        private const val FORECAST_PREF = "LOCAL_FORECAST_PREFERENCES"
    }

    override fun saveForecastData(data: ForecastLocal) {
        preferences.edit().putString(FORECAST_PREF, data.toJson()).apply()
    }

    override fun saveWeatherData(data: WeatherLocal) {
        preferences.edit().putString(WEATHER_PREF, data.toJson()).apply()
    }

    override fun readForecastData(): ForecastLocal {
        val string = preferences.getString(FORECAST_PREF, "")
        return string.orEmpty().forecastFromJson()
    }

    override fun readWeatherData(): WeatherLocal {
        val string = preferences.getString(WEATHER_PREF, "")
        return string.orEmpty().weatherFromJson()
    }

    override fun isDataAvailable(): Boolean {
        return preferences.contains(WEATHER_PREF) && preferences.contains(FORECAST_PREF)
    }
}