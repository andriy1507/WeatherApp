package com.goryachok.forecastapp.view.fragment

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.recyclerviewadapters.HourlyForecastAdapter
import com.goryachok.forecastapp.viewmodel.HourlyViewModel
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*
import javax.inject.Inject

class HourlyFragment : MyFragment(R.layout.hourly_forecast_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel: HourlyViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(
            HourlyViewModel::class.java
        )
    }

    private lateinit var adapter: HourlyForecastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                hourlyLoadingProgressBar.visibility = ProgressBar.GONE
            })
            viewModel.errorData.observe(viewLifecycleOwner, Observer { result ->
                this.context?.let { _ ->
                    hourlyLoadingProgressBar.visibility = ProgressBar.GONE
                    errorDialog.setMessage(result.exception.message)
                    errorDialog.show()
                }
            })
            viewModel.loadData.observe(viewLifecycleOwner, Observer {
                hourlyLoadingProgressBar.visibility = ProgressBar.VISIBLE
            })
        }
    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest(loc: Location?) {
        loc?.let {
            viewModel.getDataByCoordinates(loc)
        }
    }
}