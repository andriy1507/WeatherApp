package com.goryachok.forecastapp.view.fragment

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.view.recyclerviewadapters.DailyForecastAdapter
import com.goryachok.forecastapp.viewmodel.DailyViewModel
import kotlinx.android.synthetic.main.daily_forecast_fragment.*
import javax.inject.Inject

class DailyFragment : MyFragment(R.layout.daily_forecast_fragment) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        ).get(DailyViewModel::class.java)
    }

    private lateinit var forecastAdapter: DailyForecastAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.forEach { liveData ->
            liveData.observe(viewLifecycleOwner, Observer {
                dailyForecast_cityName_textView.text =
                    getString(R.string.location_template, it.city.name, it.city.country)
                forecastAdapter = DailyForecastAdapter().apply {
                    setNewItemList(it.list)
                }
                dailyForecast_recyclerView.apply {
                    adapter = forecastAdapter
                    layoutManager = LinearLayoutManager(this@DailyFragment.context)
                }
                dailyLoading_progressBar.visibility = ProgressBar.GONE
            })
        }
        viewModel.loadData.observe(viewLifecycleOwner, Observer {
            dailyLoading_progressBar.visibility = ProgressBar.VISIBLE
        })
        viewModel.errorData.observe(viewLifecycleOwner, Observer { result ->
            this.context?.let { _ ->
                dailyLoading_progressBar.visibility = ProgressBar.GONE
                errorDialog.setMessage(result.exception.message)
                errorDialog.show()
            }
        })
    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest(loc: Location?) {
        loc?.let {
            viewModel.getDataByCoordinates(it)
        }
    }
}