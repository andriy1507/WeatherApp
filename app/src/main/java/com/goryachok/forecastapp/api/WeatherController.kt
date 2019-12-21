package com.goryachok.forecastapp.api

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.goryachok.forecastapp.model.WeatherResponse
import com.goryachok.forecastapp.services.ApiService.Companion.BASE_URL
import com.goryachok.forecastapp.services.ApiService.Companion.weatherPrefs
import okhttp3.OkHttpClient
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
            editor.putString(weatherPrefs, Gson().toJson(response.body(),
                WeatherResponse::class.java))
            editor.apply()
        }else{
            Log.e(this::class.java.name,response.message())
        }
    }
    fun start(city: String) {
        val openWeatherMapAPI = OpenWeatherMapAPI.getInstance()
        val call = openWeatherMapAPI.getWeather(city)
        call.enqueue(this)
    }

    fun startWithCoord(lat:Float,lon:Float) {
        val openWeatherMapAPI = OpenWeatherMapAPI.getInstance()
        val call = openWeatherMapAPI.getWeatherByCoord(lat,lon)
        Log.i("API","CREATED API AND MADE CALL")

        call.enqueue(this)
    }


}