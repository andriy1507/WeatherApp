package com.goryachok.forecastapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.recyclerviewadapters.HourlyForecastAdapter
import com.goryachok.forecastapp.viewmodel.HourlyViewModel
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*

class HourlyFragment : MyFragment() {

    private lateinit var viewModel: HourlyViewModel

    private lateinit var adapter: HourlyForecastAdapter

    private val viewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.applicationContext?.let { HourlyViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.hourly_forecast_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HourlyViewModel::class.java)

        viewModel.getDataByCoordinates()

        viewModel.data.forEach { liveData ->
            liveData.observe(viewLifecycleOwner, Observer {
                hourlyForecast_cityName_textView.text =
                    getString(R.string.location_template, it.city.name, it.city.country)
                adapter = HourlyForecastAdapter().apply {

                    setNewItemList(it.list)
                }
                hourlyForecast_recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@HourlyFragment.context)
                }
                hourlyForecast_recyclerView.adapter = adapter
            })
        }
    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest() {
        viewModel.getDataByCoordinates()
    }
}