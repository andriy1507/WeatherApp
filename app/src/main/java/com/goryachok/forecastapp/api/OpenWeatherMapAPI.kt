package com.goryachok.forecastapp.api


import com.goryachok.forecastapp.base.RemoteEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface OpenWeatherMapAPI {

    companion object {
        const val API_KEY = "f19f55d718abb04df88d9190337bd5b9"
        const val DOMAIN = "https://api.openweathermap.org/"
    }

//    @GET("data/2.5/weather")
//    suspend fun getWeatherByCityAsync(
//        @Query("q") city: String
//    ): WeatherEntity
//
//    @GET("data/2.5/weather")
//    suspend fun getWeatherByCoordinatesAsync(
//        @Query("lat") latitude: Float,
//        @Query("lon") longitude: Float
//    ): WeatherEntity
//
//    @GET("data/2.5/forecast")
//    suspend fun getForecastByCityAsync(
//        @Query("q") city: String
//    ): ForecastEntity
//
//    @GET("data/2.5/forecast")
//    suspend fun getForecastByCoordinatesAsync(
//        @Query("lat") latitude: Float,
//        @Query("lon") longitude: Float
//    ): ForecastEntity

    @GET("data/2.5/{type}")
    suspend fun getDataByCityAsync(
        @Path("type") type: String,
        @Query("q") city: String
    ): Deferred<RemoteEntity>

    @GET("data/2.5/{type}")
    suspend fun getDataByCoordinatesAsync(
        @Path("type") type: String,
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float
    ): Deferred<RemoteEntity>
}
