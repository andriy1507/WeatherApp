package com.goryachok.forecastapp.api

import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.domain.WeatherEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): Call<WeatherEntity>

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoord(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Call<WeatherEntity>

    @GET("data/2.5/forecast")
    suspend fun getForecastByCity(
        @Query("q") city: String
    ): Call<ForecastEntity>

    @GET("data/2.5/forecast")
    suspend fun getForecastByCoord(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Call<ForecastEntity>
}