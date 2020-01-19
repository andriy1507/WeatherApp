package com.goryachok.forecastapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.di.viewmodel.DaggerViewModelComponent
import com.goryachok.forecastapp.viewmodel.CurrentViewModel
import kotlinx.android.synthetic.main.current_weather_fragment.*
import javax.inject.Inject

class CurrentFragment : MyFragment() {

    private val viewModel: CurrentViewModel by lazy { ViewModelProvider(this,viewModelFactory).get(CurrentViewModel::class.java) }

    @Inject
    lateinit var viewModelFactory:ViewModelProvider.Factory

    override fun setupDependencies() {
        DaggerViewModelComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.init()

        viewModel.data.observe(viewLifecycleOwner, Observer {
            currentTemp_textView.text = getString(R.string.temperature_template, it.main.temp)
            curLocation.text = it.city
            curWindSpeed_textView.text = getString(R.string.windSpeed_template, it.wind.speed)
            curPress_textView.text = getString(R.string.pressure_template, it.main.pressure)
            curHumid_textView.text = getString(R.string.humidity_template, it.main.humidity)
            curWindDir_textView.text = when (it.wind.deg) {
                in 0..23, in 339..360 -> "N"
                in 24..68 -> "NE"
                in 69..113 -> "E"
                in 114..158 -> "SE"
                in 159..203 -> "S"
                in 204..248 -> "SW"
                in 249..293 -> "W"
                in 294..338 -> "NW"
                else -> "-"
            }
        })
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onSearchRequest(request: String) {
        TODO()
    }

    override fun onLocationRequest() {
        TODO()
    }
}