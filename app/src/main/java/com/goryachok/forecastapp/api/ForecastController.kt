package com.goryachok.forecastapp.api

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.goryachok.forecastapp.model.forecast.ForecastResponse
import com.goryachok.forecastapp.model.weather.WeatherResponse
import com.goryachok.forecastapp.services.ApiService.Companion.BASE_URL
import com.goryachok.forecastapp.services.ApiService.Companion.forecastPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForecastController(val preferences: SharedPreferences): Callback<ForecastResponse> {
    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
        Log.e(this::class.java.name,t.stackTrace.toString())
    }

    override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
        if (response.isSuccessful){
            val editor = preferences.edit()
            editor.putString(forecastPrefs, Gson().toJson(response.body(), ForecastResponse::class.java))
            editor.apply()
        }else{
            Log.e(this::class.java.name,response.message())
        }
    }

    fun start(city:String){
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val openWeatherMapAPI = retrofit.create(OpenWeatherMapAPI::class.java)
        val call = openWeatherMapAPI.getForecast(city)
        call.enqueue(this)
    }
}