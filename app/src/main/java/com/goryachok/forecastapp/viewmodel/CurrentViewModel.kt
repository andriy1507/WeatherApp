package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.BaseRepository
import com.goryachok.forecastapp.repository.WeatherRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrentViewModel(context: Context) : ViewModel() {

    private val repository: BaseRepository<WeatherEntity> by lazy { WeatherRepository(context) }

    private val _currentData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val currentData: LiveData<WeatherEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<WeatherEntity> = MutableLiveData()
    private val searchedData: LiveData<WeatherEntity>
        get() = _searchedData

    private val _errorData: MutableLiveData<Result.Error> = MutableLiveData()
    val errorData: LiveData<Result.Error>
        get() = _errorData

    private val _loadingData: MutableLiveData<Result.Loading> = MutableLiveData()
    val loadingData: LiveData<Result.Loading>
        get() = _loadingData


    private val fusedLocationClient by lazy { FusedLocationProviderClient(context) }


    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(IO).launch {
            _loadingData.postValue(Result.Loading)
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
            _loadingData.postValue(Result.Loading)
            try {
                when (val result = repository.getDataByCoordinates(
                    location.latitude.toFloat(),
                    location.longitude.toFloat()
                )) {
                    is Result.Success -> _currentData.postValue(result.data)
                    is Result.Error -> _errorData.postValue(result)
                }
            } catch (e: Exception) {
                withContext(Main) {
                    _errorData.postValue(Result.Error(e))
                }
            }
        }
    }

    fun getCurrentLocationData() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                it?.let {
                    getDataByCoordinates(it)
                }
            }
            .addOnFailureListener {
                //TODO implement request location
                _errorData.postValue(Result.Error(it))
            }
    }
}
