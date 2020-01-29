package com.goryachok.forecastapp.data

import android.content.SharedPreferences
import com.goryachok.forecastapp.base.FORECAST_PREF
import com.goryachok.forecastapp.base.WEATHER_PREF
import com.goryachok.forecastapp.base.forecastFromJson
import com.goryachok.forecastapp.base.weatherFromJson
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(override val preferences: SharedPreferences) :
    LocalDataSource {

    override fun saveForecastData(data: ForecastEntity) {
        preferences.edit().putString(FORECAST_PREF, data.toJson()).apply()
    }

    override fun saveWeatherData(data: WeatherEntity) {
        preferences.edit().putString(WEATHER_PREF, data.toJson()).apply()
    }

    override fun readForecastData(): ForecastEntity {
        val string = preferences.getString(FORECAST_PREF, "")
        return string.orEmpty().forecastFromJson()
    }

    override fun readWeatherData(): WeatherEntity {
        val string = preferences.getString(WEATHER_PREF, "")
        return string.orEmpty().weatherFromJson()
    }

    override fun isDataAvailable(): Boolean {
        return preferences.contains(FORECAST_PREF) && preferences.contains(WEATHER_PREF)
    }
}
