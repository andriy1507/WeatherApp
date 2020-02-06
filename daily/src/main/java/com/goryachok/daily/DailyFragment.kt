package com.goryachok.daily

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.daily.di.DailyFragmentComponent
import kotlinx.android.synthetic.main.daily_forecast_fragment.*
import javax.inject.Inject

class DailyFragment private constructor() : BaseFragment(R.layout.daily_forecast_fragment) {

    @Inject
    lateinit var viewModel: DailyViewModel

    private lateinit var forecastAdapter: DailyForecastAdapter

    override fun setupDependencies() {
        DailyFragmentComponent.Initializer().init(this).inject(this)
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
                    dailyForecast_cityName_textView.text =
                        getString(R.string.location_template, it.city, it.country)
                    forecastAdapter = DailyForecastAdapter().apply {
                        setNewItemList(it.weatherList)
                    }
                    dailyForecast_recyclerView.apply {
                        adapter = forecastAdapter
                        layoutManager = LinearLayoutManager(this@DailyFragment.context)
                    }
                    dailyLoading_progressBar.visibility = ProgressBar.GONE
                })
            }
            loadData.observe(viewLifecycleOwner, Observer {
                dailyLoading_progressBar.visibility = View.VISIBLE
            })
            errorData.observe(viewLifecycleOwner, Observer { error ->
                dailyLoading_progressBar.visibility = View.GONE
                this@DailyFragment.context?.let {
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

        fun newInstance() = DailyFragment().apply {
            arguments = Bundle()
        }
    }
}