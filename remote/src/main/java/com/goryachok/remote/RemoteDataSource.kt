package com.goryachok.remote

import com.goryachok.remote.model.ForecastEntity
import com.goryachok.remote.model.WeatherEntity
import com.goryachok.remote.model.ApiResponse

interface RemoteDataSource {

    val api: WeatherApiService

    suspend fun getWeatherByCity(city: String): ApiResponse<WeatherEntity>

    suspend fun getForecastByCity(city: String): ApiResponse<ForecastEntity>

    suspend fun getWeatherByCoordinates(lat: Float, lon: Float): ApiResponse<WeatherEntity>

    suspend fun getForecastByCoordinates(lat: Float, lon: Float): ApiResponse<ForecastEntity>
}