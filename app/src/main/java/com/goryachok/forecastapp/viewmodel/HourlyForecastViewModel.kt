package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.pojo.ForecastEntity

class HourlyForecastViewModel : ViewModel() {

    var data: MutableLiveData<ForecastEntity> = MutableLiveData()
}