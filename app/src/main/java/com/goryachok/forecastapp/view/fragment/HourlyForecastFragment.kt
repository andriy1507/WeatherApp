package com.goryachok.forecastapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.base.PagerFragment
import com.goryachok.forecastapp.view.adapters.HourlyForecastAdapter
import com.goryachok.forecastapp.viewmodel.HourlyForecastViewModel
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*
import javax.inject.Inject

class HourlyForecastFragment : PagerFragment() {
    @Inject
    lateinit var viewModel: HourlyForecastViewModel

    private lateinit var adapter: HourlyForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.update(it.list)
            hourlyForecast_cityName_textView.text =
                getString(R.string.location_template, it.city.name, it.city.country)
        })
        return inflater.inflate(R.layout.hourly_forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        hourlyForecast_recyclerView.layoutManager = LinearLayoutManager(context)
        hourlyForecast_recyclerView.adapter = HourlyForecastAdapter(viewModel.data.value!!.list)
    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest() {
        viewModel.getDataByLocation()
    }
}
