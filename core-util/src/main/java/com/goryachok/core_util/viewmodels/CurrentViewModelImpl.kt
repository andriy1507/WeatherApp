package com.goryachok.core_util.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goryachok.core.model.ResponseResult
import com.goryachok.core.model.WeatherDomain
import com.goryachok.core.repository.WeatherRepository
import com.goryachok.core.viewmodels.CurrentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentViewModelImpl : CurrentViewModel() {

    private val currentData = MutableLiveData<WeatherDomain>()
    private val searchedData = MutableLiveData<WeatherDomain>()

    private val _loadData: MutableLiveData<ResponseResult.Loading> = MutableLiveData()
    override val loadData: LiveData<ResponseResult.Loading>
        get() = _loadData

    private val _errorData: MutableLiveData<ResponseResult.Error> = MutableLiveData()
    override val errorData: LiveData<ResponseResult.Error>
        get() = _errorData

    override val data: List<MutableLiveData<WeatherDomain>> = listOf(currentData, searchedData)

    @Inject
    lateinit var repository: WeatherRepository

    override fun getCurrentLocationData(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            try {
                when (val result = repository.getDataByCoordinates(
                    location.latitude.toFloat(),
                    location.longitude.toFloat()
                )) {
                    is ResponseResult.Success<*> -> {
                        currentData.postValue(result.data as WeatherDomain)
                    }
                    is ResponseResult.Error -> _errorData.postValue(result)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorData.postValue(ResponseResult.Error(e))
                }
            }
        }
    }

    override fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            try {
                when (val result = repository.getDataByCity(city)) {
                    is ResponseResult.Success<*> -> withContext(Dispatchers.Main) {
                        currentData.postValue(
                            result.data as WeatherDomain
                        )
                    }
                    is ResponseResult.Error -> withContext(Dispatchers.Main) {
                        _errorData.postValue(
                            result
                        )
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _errorData.postValue(ResponseResult.Error(e))
                }
            }
        }
    }
}