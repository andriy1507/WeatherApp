package com.goryachok.core.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.core.model.ResponseResult
import com.goryachok.core.model.WeatherDomain

abstract class CurrentViewModel : ViewModel() {

    abstract val data: List<MutableLiveData<WeatherDomain>>

    abstract val loadData: LiveData<ResponseResult.Loading>

    abstract val errorData: LiveData<ResponseResult.Error>

    abstract fun getCurrentLocationData(location: Location)

    abstract fun getDataByCity(city: String)
}