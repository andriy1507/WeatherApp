package com.goryachok.forecastapp.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.fragment.ForecastPagerAdapter
import com.goryachok.forecastapp.model.ForecastResponse
import com.goryachok.forecastapp.model.WeatherResponse
import com.goryachok.forecastapp.services.ApiService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var weather: WeatherResponse
        lateinit var forecast: ForecastResponse
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonWeather = getSharedPreferences(ApiService.preferencesName, Context.MODE_PRIVATE).getString(
            ApiService.weatherPrefs, "")
        val jsonForecast = getSharedPreferences(ApiService.preferencesName, Context.MODE_PRIVATE).getString(
            ApiService.forecastPrefs,"")

        weather = Gson().fromJson(jsonWeather,
            WeatherResponse::class.java)
        forecast = Gson().fromJson(jsonForecast,
            ForecastResponse::class.java)

        setContentView(R.layout.activity_main)

        forecast_viewPager.adapter = ForecastPagerAdapter(supportFragmentManager)

    }
}
