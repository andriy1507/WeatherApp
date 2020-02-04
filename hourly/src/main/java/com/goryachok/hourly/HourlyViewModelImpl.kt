package com.goryachok.hourly

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.core.model.Forecast
import com.goryachok.core.model.ResponseResult
import com.goryachok.core.model.Weather
import com.goryachok.core.repository.ForecastRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HourlyViewModelImpl : ViewModel() {

    private val currentData = MutableLiveData<Forecast>()
    private val searchedData = MutableLiveData<Forecast>()

    private val _loadData: MutableLiveData<ResponseResult.Loading> = MutableLiveData()
    val loadData: LiveData<ResponseResult.Loading>
        get() = _loadData

    private val _errorData: MutableLiveData<ResponseResult.Error> = MutableLiveData()
    val errorData: LiveData<ResponseResult.Error>
        get() = _errorData

    val data: List<MutableLiveData<Forecast>> = listOf(currentData, searchedData)

    @Inject
    lateinit var repository: ForecastRepository

    fun getCurrentLocationData(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            when (val result: ResponseResult<*> = repository.getDataByCoordinates(
                location.latitude.toFloat(),
                location.longitude.toFloat()
            )) {
                is ResponseResult.Success<*> -> {
                    val newList = mutableListOf<Weather>()
                    result.let {
                        result as ResponseResult.Success<Forecast>
                        for (index in 0..7) {
                            newList.add(result.data.weatherList[index])
                        }
                        withContext(Dispatchers.Main) {
                            currentData.postValue(result.data.copy(weatherList = newList))
                        }
                    }
                }
                is ResponseResult.Error -> withContext(Dispatchers.Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }

    fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            when (val result: ResponseResult<*> = repository.getDataByCity(city)) {
                is ResponseResult.Success<*> -> {
                    val newList = mutableListOf<Weather>()
                    result.let {
                        result as ResponseResult.Success<Forecast>
                        for (index in 0..7) {
                            newList.add(result.data.weatherList[index])
                        }
                        withContext(Dispatchers.Main) {
                            currentData.postValue(result.data.copy(weatherList = newList))
                        }
                    }
                }
                is ResponseResult.Error -> withContext(Dispatchers.Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }
}