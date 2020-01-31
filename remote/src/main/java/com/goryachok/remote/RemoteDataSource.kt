package com.goryachok.remote

import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.ForecastEntity
import com.goryachok.remote.model.WeatherEntity

interface RemoteDataSource {

    suspend fun getWeatherByCity(city: String): ApiResponse<WeatherEntity>

    suspend fun getForecastByCity(city: String): ApiResponse<ForecastEntity>

    suspend fun getWeatherByCoordinates(lat: Float, lon: Float): ApiResponse<WeatherEntity>

    suspend fun getForecastByCoordinates(lat: Float, lon: Float): ApiResponse<ForecastEntity>
}