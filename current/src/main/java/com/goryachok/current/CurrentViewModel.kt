package com.goryachok.current

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.core.model.ResponseResult
import com.goryachok.core.model.Weather
import com.goryachok.core.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val currentData = MutableLiveData<Weather>()
    private val searchedData = MutableLiveData<Weather>()

    private val _loadData: MutableLiveData<ResponseResult.Loading> = MutableLiveData()
    val loadData: LiveData<ResponseResult.Loading>
        get() = _loadData

    private val _errorData: MutableLiveData<ResponseResult.Error> = MutableLiveData()
    val errorData: LiveData<ResponseResult.Error>
        get() = _errorData

    val data: List<MutableLiveData<Weather>> = listOf(currentData, searchedData)

    fun getCurrentLocationData(location: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            try {
                when (val result = repository.getDataByCoordinates(
                    location.latitude.toFloat(),
                    location.longitude.toFloat()
                )) {
                    is ResponseResult.Success<*> -> {
                        currentData.postValue(result.data as Weather)
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


    fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(ResponseResult.Loading)
            try {
                when (val result = repository.getDataByCity(city)) {
                    is ResponseResult.Success<*> -> withContext(Dispatchers.Main) {
                        currentData.postValue(
                            result.data as Weather
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


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(private val repository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrentViewModel(repository) as T
        }
    }
}