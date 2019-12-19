package com.goryachok.forecastapp.api

import com.google.gson.GsonBuilder
import com.goryachok.forecastapp.model.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ControllerAPI: Callback<Weather> {

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun start(){

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()

        val openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI::class.java)

        val weather = openWeatherMapAPI.getCurrentWeather(TODO("City name input"))

        weather.enqueue(this)
    }

    override fun onFailure(call: Call<Weather>?, t: Throwable?) {

    }

    override fun onResponse(call: Call<Weather>?, response: Response<Weather>?) {

    }
}