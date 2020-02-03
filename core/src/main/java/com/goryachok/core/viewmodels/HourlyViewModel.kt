package com.goryachok.core.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.core.model.ForecastDomain
import com.goryachok.core.model.ResponseResult

abstract class HourlyViewModel : ViewModel() {

    abstract val data: List<MutableLiveData<ForecastDomain>>

    abstract val loadData: LiveData<ResponseResult.Loading>

    abstract val errorData: LiveData<ResponseResult.Error>

    abstract fun getCurrentLocationData(location: Location)

    abstract fun getDataByCity(city: String)
}