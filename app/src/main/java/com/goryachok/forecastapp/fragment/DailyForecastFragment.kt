package com.goryachok.forecastapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.goryachok.forecastapp.adapters.DailyForecastAdapter
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.activities.MainActivity
import kotlinx.android.synthetic.main.daily_forecast_fragment.*

class DailyForecastFragment :Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.daily_forecast_fragment,container,false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val forecast = MainActivity.forecast

        dailyForecast_recyclerView.layoutManager = LinearLayoutManager(this.context)
        dailyForecast_recyclerView.adapter = DailyForecastAdapter(forecast.list)
        val dividerItemDecoration = DividerItemDecoration(dailyForecast_recyclerView.context,HORIZONTAL)
        dailyForecast_recyclerView.addItemDecoration(dividerItemDecoration)
        dailyForecast_cityName_textView.text = "${forecast.city.name}, ${forecast.city.country}"
        super.onActivityCreated(savedInstanceState)
    }
}