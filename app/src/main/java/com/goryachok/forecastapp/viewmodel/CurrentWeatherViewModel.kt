package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.pojo.WeatherEntity

class CurrentWeatherViewModel : ViewModel() {
    var data: MutableLiveData<WeatherEntity> = MutableLiveData()
}