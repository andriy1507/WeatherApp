package com.goryachok.forecastapp.view.fragment

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.utils.Converter
import com.goryachok.forecastapp.viewmodel.CurrentViewModel
import kotlinx.android.synthetic.main.current_weather_fragment.*

class CurrentFragment : MyFragment() {

    override val viewModel: CurrentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(
            CurrentViewModel::class.java
        )
    }

    private val viewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return activity?.applicationContext?.let { CurrentViewModel(it) } as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.current_weather_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.forEach { liveData ->
            liveData.observe(viewLifecycleOwner, Observer {
                it.let {
                    currentTemp_textView.text =
                        getString(R.string.temperature_template, it.main.temp)
                    curLocation.text = it.city
                    curWindSpeed_textView.text =
                        getString(R.string.windSpeed_template, it.wind.speed)
                    curPress_textView.text = getString(R.string.pressure_template, it.main.pressure)
                    curHumid_textView.text = getString(R.string.humidity_template, it.main.humidity)
                    curWindDir_textView.text = Converter.convertDegreesToDirection(it.wind.deg)
                }
                loadDialog.dismiss()
            })
        }

    }

    override fun onSearchRequest(request: String) {
        viewModel.getDataByCity(request)
        loadDialog.dismiss()
    }

    override fun onLocationRequest(loc: Location) {
        viewModel.getCurrentLocationData(loc)
        loadDialog.dismiss()
    }
}