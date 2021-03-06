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
import com.goryachok.forecastapp.adapters.HourlyForecastAdapter
import com.goryachok.forecastapp.viewmodel.HourlyForecastViewModel
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*

class HourlyForecastFragment : Fragment() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(HourlyForecastViewModel::class.java) }
    private lateinit var adapter: HourlyForecastAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hourly_forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = HourlyForecastAdapter(viewModel.data.value!!.list)
        hourlyForecast_recyclerView.layoutManager = LinearLayoutManager(context)
        hourlyForecast_recyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.update(it.list)
            hourlyForecast_cityName_textView.text = getString(R.string.location_template,it.city.name,it.city.country)
        })
    }
}