package com.goryachok.forecastapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.base.PagerFragment
import com.goryachok.forecastapp.ui.adapters.DailyForecastAdapter
import com.goryachok.forecastapp.viewmodel.DailyForecastViewModel
import kotlinx.android.synthetic.main.daily_forecast_fragment.*
import javax.inject.Inject

class DailyForecastFragment : PagerFragment() {


    @Inject
    lateinit var viewModel: DailyForecastViewModel

    private lateinit var adapter: DailyForecastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.update(it.list)
            dailyForecast_cityName_textView.text =
                getString(R.string.location_template, it.city.name, it.city.country)
        })
        return inflater.inflate(R.layout.daily_forecast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter =
            DailyForecastAdapter(viewModel.data.value!!.list)
        dailyForecast_recyclerView.layoutManager = LinearLayoutManager(context)
        dailyForecast_recyclerView.adapter = adapter

    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest() {
        viewModel.getDataByLocation()
    }
}