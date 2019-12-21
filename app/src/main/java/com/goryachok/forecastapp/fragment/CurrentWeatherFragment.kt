package com.goryachok.forecastapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.activities.MainActivity
import kotlinx.android.synthetic.main.current_weather_fragment.*

class CurrentWeatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val weather = MainActivity.weather
        currentTemp_textView.text = weather.main.temp.toString()
        curMinTemp_textView.text = weather.main.tempMin.toString()
        curMaxTemp_textView.text = weather.main.tempMax.toString()
        curPress_textView.text = weather.main.pressure.toString()
        curHumid_textView.text = weather.main.humidity.toString()
        curLocation.text = weather.name
        val time = weather.dt.toLong()+weather.timezone

        updateTime.text = time.toString()
        super.onActivityCreated(savedInstanceState)
    }
}

