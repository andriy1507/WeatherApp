package com.goryachok.forecastapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.viewmodel.CurrentViewModel
import kotlinx.android.synthetic.main.current_weather_fragment.*

class CurrentFragment : MyFragment() {

    private lateinit var viewModel: CurrentViewModel

    val viewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.applicationContext?.let { CurrentViewModel(it) } as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            CurrentViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.forEach { liveData ->
            liveData.observe(viewLifecycleOwner, Observer {
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
        }
        viewModel.getDataByCity("Lviv")

    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
    }

    override fun onLocationRequest() {
        TODO()
    }
}