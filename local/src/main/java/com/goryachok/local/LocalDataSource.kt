package com.goryachok.local

import android.content.SharedPreferences
import com.goryachok.local.model.LocalForecast
import com.goryachok.local.model.LocalWeather

interface LocalDataSource {

    val preferences:SharedPreferences

    fun saveForecastData(data: LocalForecast)

    fun saveWeatherData(data: LocalWeather)

    fun readForecastData(): LocalForecast

    fun readWeatherData(): LocalWeather

    fun isDataAvailable(): Boolean
}