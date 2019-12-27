package com.goryachok.forecastapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.adapters.DailyForecastAdapter
import com.goryachok.forecastapp.viewmodel.DailyForecastViewModel
import kotlinx.android.synthetic.main.daily_forecast_fragment.*

class DailyForecastFragment : Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(DailyForecastViewModel::class.java)}
    private lateinit var adapter: DailyForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.daily_forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = DailyForecastAdapter(viewModel.data.value!!.list)
        dailyForecast_recyclerView.layoutManager = LinearLayoutManager(context)
        dailyForecast_recyclerView.adapter = adapter
        viewModel.data.observe(this, Observer {
            adapter.update(it.list)
            dailyForecast_cityName_textView.text=getString(R.string.location_template,it.city.name,it.city.country)
        })
    }
}