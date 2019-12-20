package com.goryachok.forecastapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.activities.MainActivity
import com.goryachok.forecastapp.model.forecast.ForecastResponse
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*

class HourlyForecastFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hourly_forecast_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val forecast = MainActivity.forecast

        hourly_text_view.text = Gson().toJson(forecast,ForecastResponse::class.java)

        super.onActivityCreated(savedInstanceState)
    }
}