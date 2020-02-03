package com.goryachok.remote

import com.goryachok.remote.model.ApiResponse
import com.goryachok.remote.model.ForecastRemote
import com.goryachok.remote.model.WeatherRemote

interface RemoteDataSource {

    suspend fun getWeatherByCity(city: String): ApiResponse<WeatherRemote>

    suspend fun getForecastByCity(city: String): ApiResponse<ForecastRemote>

    suspend fun getWeatherByCoordinates(lat: Float, lon: Float): ApiResponse<WeatherRemote>

    suspend fun getForecastByCoordinates(lat: Float, lon: Float): ApiResponse<ForecastRemote>
}