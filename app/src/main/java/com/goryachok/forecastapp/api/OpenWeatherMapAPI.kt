package com.goryachok.forecastapp.api

import com.goryachok.forecastapp.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherMapAPI {

    @GET("weather?appid=95c833ccc06be137270b7c86710baba8&units=metric")
    fun getCurrentWeather(@Query("q") city: String): Call<Weather>

    @GET("hourly?appid=95c833ccc06be137270b7c86710baba8&units=metric")
    fun getHourlyWeather(@Query("q") city: String): Call<List<Weather>>

    @GET("daily?appid=95c833ccc06be137270b7c86710baba8&units=metric")
    fun getDailyWEather(@Query("q") city: String): Call<List<Weather>>

}