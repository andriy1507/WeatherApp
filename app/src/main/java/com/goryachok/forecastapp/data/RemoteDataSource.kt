package com.goryachok.forecastapp.data

import com.goryachok.forecastapp.api.ClientBuilder
import com.goryachok.forecastapp.pojo.ForecastEntity
import com.goryachok.forecastapp.pojo.WeatherEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class RemoteDataSource {

    private val api = ClientBuilder().build()

    fun getWeatherData(
        city: String? = null,
        lon: Float = 0.0F,
        lat: Float = 0.0F
    ): WeatherEntity {
        lateinit var response: WeatherEntity
        if (city != null) {
            CoroutineScope(IO).launch {
                response = api.getWeatherByCityAsync(city)
            }
        } else {
            CoroutineScope(IO).launch {
                response = api.getWeatherByCoordinatesAsync(lon, lat)
            }
        }
        return response
    }

    fun getForecastData(
        city: String? = null,
        lon: Float = 0.0F,
        lat: Float = 0.0F
    ): ForecastEntity {
        lateinit var response: ForecastEntity
        if (city != null) {
            CoroutineScope(IO).launch {
                response = api.getForecastByCityAsync(city)
            }
        } else {
            CoroutineScope(IO).launch {
                response = api.getForecastByCoordinatesAsync(lon, lat)
            }
        }
        return response
    }
}