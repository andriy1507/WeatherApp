package com.goryachok.remote

import com.goryachok.remote.model.ForecastEntity
import com.goryachok.remote.model.WeatherEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): Response<WeatherEntity>

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Response<WeatherEntity>

    @GET("data/2.5/forecast")
    suspend fun getForecastByCity(
        @Query("q") city: String
    ): Response<ForecastEntity>

    @GET("data/2.5/forecast")
    suspend fun getForecastByCoordinates(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Response<ForecastEntity>
}