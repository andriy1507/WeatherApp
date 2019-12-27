package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.model.WeatherResponse

class CurrentWeatherViewModel : ViewModel() {
    var data: MutableLiveData<WeatherResponse> = MutableLiveData()
    private val repository by lazy { WeatherApplication.repository }

    init {

        data.value = repository.localWeatherData.value

        repository.apply {
            lastSearchWeatherData.observeForever {
                data.postValue(it)
            }
            localWeatherData.observeForever {
                data.postValue(it)
            }
        }
    }

}