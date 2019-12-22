package com.goryachok.forecastapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.activities.MainActivity
import com.goryachok.forecastapp.services.ApiService
import kotlinx.android.synthetic.main.current_weather_fragment.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CurrentWeatherFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val weather = MainActivity.weather
        currentTemp_textView.text = weather.main.temp.toString() + "\u00B0"
        curWindSpeed_textView.text = weather.wind.speed.toString() + " m/s"
        curWindDir_textView.text = when (weather.wind.deg) {
            in 0..23, in 339..360 -> "N"
            in 24..68 -> "NE"
            in 69..113 -> "E"
            in 114..158 -> "SE"
            in 159..203 -> "S"
            in 204..248 -> "SW"
            in 249..293 -> "W"
            in 294..338 -> "NW"
            else -> "-"
        }
        curPress_textView.text = weather.main.pressure.toString() + "kPa"
        curHumid_textView.text = weather.main.humidity.toString() + "%"
        curLocation.text = weather.name
        super.onActivityCreated(savedInstanceState)
    }
}

