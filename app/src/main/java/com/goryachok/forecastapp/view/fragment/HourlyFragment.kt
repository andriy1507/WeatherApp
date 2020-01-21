package com.goryachok.forecastapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.recyclerviewadapters.HourlyForecastAdapter
import com.goryachok.forecastapp.viewmodel.HourlyViewModel

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

    init {
        Log.d(BuildConfig.TAG, this::class.java.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.hourly_forecast_fragment, container, false)
        Log.d(BuildConfig.TAG, "${this::class.java.name} layout inflated")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HourlyViewModel::class.java)
        //        adapter = TODO How to pass data to adapter correctly
        // hourlyForecast_recyclerView.adapter = adapter
//        viewModel.data.forEach { liveData ->
//            liveData.observe(viewLifecycleOwner, Observer {
//                hourlyForecast_cityName_textView.text =
//                    getString(R.string.location_template, it.city.name, it.city.country)
//                adapter.update(it.list)
//            })
//        }
        Log.d(BuildConfig.TAG, "${this::class.java.name} view created")
    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest() {
        viewModel.getDataByCoord()
    }
}