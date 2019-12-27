package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.WeatherApplication
import com.goryachok.forecastapp.model.ForecastResponse

class DailyForecastViewModel : ViewModel() {
    var data: MutableLiveData<ForecastResponse> = MutableLiveData()
    private val repository by lazy { WeatherApplication.repository }

    init {

        data.value = repository.localForecastData.value

        repository.apply {
            lastSearchForecastData.observeForever {
                data.postValue(it)
            }
            localForecastData.observeForever {
                data.postValue(it)
            }
        }
    }
}