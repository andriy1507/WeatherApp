package com.goryachok.forecastapp.data

import com.goryachok.forecastapp.api.WeatherApiService
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result

interface RemoteDataSource {

    val apiService: WeatherApiService

    suspend fun getWeatherByCity(city: String): Result<WeatherEntity>

    suspend fun getForecastByCity(city: String): Result<ForecastEntity>

    suspend fun getWeatherByCoordinates(lat: Float, lon: Float): Result<WeatherEntity>

    suspend fun getForecastByCoordinates(lat: Float, lon: Float): Result<ForecastEntity>
}