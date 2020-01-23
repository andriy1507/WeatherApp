package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    @SuppressLint("MissingPermission")
    private val location =
        (context.getSystemService(LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
            LocationManager.GPS_PROVIDER
        )

    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(IO).launch {
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

    fun getDataByCoordinates() {
        CoroutineScope(IO).launch {
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

    fun initializeData() {
        getDataByCoordinates()
    }
}
