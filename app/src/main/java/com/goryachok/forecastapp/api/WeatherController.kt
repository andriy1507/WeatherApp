package com.goryachok.forecastapp.api

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.goryachok.forecastapp.model.weather.WeatherResponse
import com.goryachok.forecastapp.services.ApiService.Companion.BASE_URL
import com.goryachok.forecastapp.services.ApiService.Companion.weatherPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherController(val preferences: SharedPreferences): Callback<WeatherResponse> {
    override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
        Log.e(this::class.java.name,t.stackTrace.toString())
    }

    override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
        if (response.isSuccessful){
            val editor = preferences.edit()
            editor.putString(weatherPrefs, Gson().toJson(response.body(),WeatherResponse::class.java))
            editor.apply()
        }else{
            Log.e(this::class.java.name,response.message())
        }
    }
    fun start(city: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI::class.java)
        val call = openWeatherMapAPI.getWeather(city)
        call.enqueue(this)
    }
}