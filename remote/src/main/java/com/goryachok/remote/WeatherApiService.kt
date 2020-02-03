package com.goryachok.remote

import com.goryachok.remote.model.ForecastRemote
import com.goryachok.remote.model.WeatherRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): Response<WeatherRemote>

    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Response<WeatherRemote>

    @GET("data/2.5/forecast")
    suspend fun getForecastByCity(
        @Query("q") city: String
    ): Response<ForecastRemote>

    @GET("data/2.5/forecast")
    suspend fun getForecastByCoordinates(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Response<ForecastRemote>
}