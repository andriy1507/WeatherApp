package com.goryachok.core_util

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goryachok.core.base.ForecastRepository
import com.goryachok.core.base.viewmodels.DailyViewModel
import com.goryachok.core.getEveryEighth
import com.goryachok.core.model.ForecastDomain
import com.goryachok.core.model.ResponseResult
import com.goryachok.core.model.WeatherDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyViewModelImpl : DailyViewModel() {

    private val currentData = MutableLiveData<ForecastDomain>()
    private val searchedData = MutableLiveData<ForecastDomain>()

    private val _loadData: MutableLiveData<ResponseResult.Loading> = MutableLiveData()
    override val loadData: LiveData<ResponseResult.Loading>
        get() = _loadData

    private val _errorData: MutableLiveData<ResponseResult.Error> = MutableLiveData()
    override val errorData: LiveData<ResponseResult.Error>
        get() = _errorData

    override val data: List<MutableLiveData<ForecastDomain>> = listOf(currentData, searchedData)

    @Inject
    lateinit var repository: ForecastRepository

    override fun getCurrentLocationData(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            when (val result: ResponseResult<*> = repository.getDataByCoordinates(
                location.latitude.toFloat(),
                location.longitude.toFloat()
            )) {
                is ResponseResult.Success<*> -> {
                    result as ResponseResult.Success<ForecastDomain>
                    val newList = result.data.weatherList.getEveryEighth()
                    withContext(Dispatchers.Main) {
                        currentData.postValue(result.data.copy(weatherList = newList))
                    }
                }
                is ResponseResult.Error -> withContext(Dispatchers.Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }

    override fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            when (val result: ResponseResult<*> = repository.getDataByCity(city)) {
                is ResponseResult.Success<*> -> {
                    result as ResponseResult.Success<ForecastDomain>
                    val newList: List<WeatherDomain> =
                        result.data.weatherList.getEveryEighth()
                    withContext(Dispatchers.Main) {
                        currentData.postValue(result.data.copy(weatherList = newList))
                    }
                }
                is ResponseResult.Error -> withContext(Dispatchers.Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }
}