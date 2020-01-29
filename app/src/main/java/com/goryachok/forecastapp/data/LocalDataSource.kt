package com.goryachok.forecastapp.data

import android.content.SharedPreferences
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity

interface LocalDataSource {

    val preferences: SharedPreferences

    fun saveForecastData(data: ForecastEntity)

    fun saveWeatherData(data: WeatherEntity)

    fun readForecastData(): ForecastEntity

    fun readWeatherData(): WeatherEntity

    fun isDataAvailable(): Boolean
}