package com.goryachok.forecastapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.fragment.ForecastPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {

        lateinit var currentWeather:String
        lateinit var hourlyWeather:String
        lateinit var dailyWeather:String

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forecast_viewPager.adapter =
            ForecastPagerAdapter(
                supportFragmentManager
            )
    }

    private fun getDataFromPrefs(key:String):String = ""

}
