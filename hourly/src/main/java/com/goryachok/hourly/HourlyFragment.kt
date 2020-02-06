package com.goryachok.hourly

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.hourly.di.HourlyFragmentComponent
import kotlinx.android.synthetic.main.hourly_forecast_fragment.*
import javax.inject.Inject

class HourlyFragment private constructor() : BaseFragment(R.layout.hourly_forecast_fragment) {

    @Inject
    lateinit var viewModel: HourlyViewModel

    private lateinit var forecastAdapter: HourlyForecastAdapter

    override fun setupDependencies() {
        HourlyFragmentComponent.Initializer().init(this).inject(this)
    }

    override fun onSearchRequest(query: String) {
        viewModel.getDataByCity(query)
    }

    override fun onLocationRequest(location: Location) {
        viewModel.getCurrentLocationData(location)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            data.forEach { liveData ->
                liveData.observe(viewLifecycleOwner, Observer {
                    hourlyForecast_cityName_textView.text =
                        getString(R.string.location_template, it.city, it.country)
                    forecastAdapter = HourlyForecastAdapter().apply {
                        setNewItemList(it.weatherList)
                    }
                    hourlyForecast_recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@HourlyFragment.context)
                        adapter = forecastAdapter
                    }
                    hourlyLoadingProgressBar.visibility = ProgressBar.GONE
                })
            }
            loadData.observe(viewLifecycleOwner, Observer {
                hourlyLoadingProgressBar.visibility = View.VISIBLE
            })
            errorData.observe(viewLifecycleOwner, Observer { error ->
                hourlyLoadingProgressBar.visibility = View.GONE
                this@HourlyFragment.context?.let {
                    AlertDialog.Builder(it).setTitle(getString(R.string.error))
                        .setMessage(error.exception.message)
                        .setPositiveButton(getString(R.string.close_button)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            })
        }
    }

    companion object {
        private const val CITY_NAME_KEY = "city_name"

        fun newInstance() = HourlyFragment().apply {
            arguments = Bundle()
        }
    }
}