package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.BaseRepository
import com.goryachok.forecastapp.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class CurrentViewModel(context: Context) : MyViewModel() {

    private val repository: BaseRepository<WeatherEntity> by lazy { WeatherRepository(context) }

    private val _currentData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val currentData: LiveData<WeatherEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val searchedData: LiveData<WeatherEntity>
        get() = _searchedData

    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(IO).launch {
            _loadData.postValue(Result.Loading)
            try {
                when (val result = repository.getDataByCity(city)) {
                    is Result.Success -> withContext(Main) { _currentData.postValue(result.data) }
                    is Result.Error -> withContext(Main) { _errorData.postValue(result) }
                }
            } catch (e: Exception) {
                withContext(Main) {
                    _errorData.postValue(Result.Error(e))
                }
            }
        }
    }

    private fun getDataByCoordinates(location: Location) {
        CoroutineScope(IO).launch {
            _loadData.postValue(Result.Loading)
            try {
                when (val result = repository.getDataByCoordinates(
                    location.latitude.toFloat(),
                    location.longitude.toFloat()
                )) {
                    is Result.Success -> {
                        Timber.d("System time: ${System.currentTimeMillis()} Api time: ${result.data.date} Timezone: ${result.data.timezone}")
                        _currentData.postValue(result.data)
                    }
                    is Result.Error -> _errorData.postValue(result)
                }
            } catch (e: Exception) {
                withContext(Main) {
                    _errorData.postValue(Result.Error(e))
                }
            }
        }
    }

    fun getCurrentLocationData(loc: Location) {
        getDataByCoordinates(loc)
    }
}
