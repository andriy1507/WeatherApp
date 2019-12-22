package com.goryachok.forecastapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.goryachok.forecastapp.adapters.HourlyForecastAdapter
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.activities.MainActivity
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*

class HourlyForecastFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hourly_forecast_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val forecast = MainActivity.forecast

        hourlyForecast_recyclerView.layoutManager = LinearLayoutManager(this.context)
        hourlyForecast_recyclerView.adapter = HourlyForecastAdapter(forecast.list)
        hourlyForecast_recyclerView.addItemDecoration(DividerItemDecoration(this.context,HORIZONTAL))
        hourlyForecast_cityName_textView.text = "${forecast.city.name}, ${forecast.city.country}"

        super.onActivityCreated(savedInstanceState)
    }
}