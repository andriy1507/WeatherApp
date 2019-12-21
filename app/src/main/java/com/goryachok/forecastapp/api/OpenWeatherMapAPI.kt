package com.goryachok.forecastapp.api

import com.goryachok.forecastapp.model.ForecastResponse
import com.goryachok.forecastapp.model.WeatherResponse
import com.goryachok.forecastapp.services.ApiService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherMapAPI {

    @GET("forecast?appid=f19f55d718abb04df88d9190337bd5b9&units=metric")
    fun getForecast(@Query("q") city: String): Call<ForecastResponse>

    @GET("weather?appid=f19f55d718abb04df88d9190337bd5b9&units=metric")
    fun getWeather(@Query("q") city: String): Call<WeatherResponse>

    @GET("forecast?appid=f19f55d718abb04df88d9190337bd5b9&units=metric")
    fun getForecastByCoord(@Query("lat")latitude:Float,@Query("lon")longitude:Float):Call<ForecastResponse>

    @GET("weather?appid=f19f55d718abb04df88d9190337bd5b9&units=metric")
    fun getWeatherByCoord(@Query("lat")latitude:Float,@Query("lon")longitude:Float):Call<WeatherResponse>

    companion object{
        fun getInstance(): OpenWeatherMapAPI {
            val retrofitBuilder = Retrofit.Builder()
            retrofitBuilder.baseUrl(ApiService.BASE_URL)
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create())
            retrofitBuilder.client(OkHttpClient.Builder().build())
            return  retrofitBuilder.build().create(OpenWeatherMapAPI::class.java)
        }
    }
}