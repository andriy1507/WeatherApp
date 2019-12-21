package com.goryachok.forecastapp.api

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.goryachok.forecastapp.model.ForecastResponse
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
        val openWeatherMapAPI = OpenWeatherMapAPI.getInstance()
        val call = openWeatherMapAPI.getForecast(city)
        call.enqueue(this)
    }

    fun startWithCoord(lat:Float,lon:Float) {
        val openWeatherMapAPI = OpenWeatherMapAPI.getInstance()
        val call = openWeatherMapAPI.getForecastByCoord(lat,lon)
        call.enqueue(this)
    }
}