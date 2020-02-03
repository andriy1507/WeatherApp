package com.goryachok.local

import com.goryachok.local.model.ForecastLocal
import com.goryachok.local.model.WeatherLocal

interface LocalDataSource {

    fun saveForecastData(data: ForecastLocal)

    fun saveWeatherData(data: WeatherLocal)

    fun readForecastData(): ForecastLocal

    fun readWeatherData(): WeatherLocal

    fun isDataAvailable(): Boolean
}