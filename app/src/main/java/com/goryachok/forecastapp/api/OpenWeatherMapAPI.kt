package com.goryachok.forecastapp.api


import com.goryachok.forecastapp.base.RemoteEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface OpenWeatherMapAPI {

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
