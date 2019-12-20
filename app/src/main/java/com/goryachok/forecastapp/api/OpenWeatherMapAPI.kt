package com.goryachok.forecastapp.api

import com.goryachok.forecastapp.model.forecast.ForecastResponse
import com.goryachok.forecastapp.model.weather.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherMapAPI {

    @GET("forecast?appid=95c833ccc06be137270b7c86710baba8&units=metric")
    fun getForecast(@Query("q") city: String): Call<ForecastResponse>

    @GET("weather?appid=95c833ccc06be137270b7c86710baba8&units=metric")
    fun getWeather(@Query("q") city: String): Call<WeatherResponse>

}