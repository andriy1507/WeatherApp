package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.pojo.ForecastEntity

class DailyForecastViewModel : ViewModel() {
    var data: MutableLiveData<ForecastEntity> = MutableLiveData()

}