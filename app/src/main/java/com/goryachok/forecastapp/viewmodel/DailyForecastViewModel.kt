package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.pojo.ForecastResponse

class DailyForecastViewModel : ViewModel() {
    var data: MutableLiveData<ForecastResponse> = MutableLiveData()

}