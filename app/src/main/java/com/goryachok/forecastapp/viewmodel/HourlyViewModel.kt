package com.goryachok.forecastapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.model.domain.Forecast
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.BaseRepository
import com.goryachok.forecastapp.repository.ForecastRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HourlyViewModel(context: Context) : ViewModel() {

    private val repository: BaseRepository<ForecastEntity> by lazy { ForecastRepository(context) }

    private val _currentData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val currentData: LiveData<ForecastEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val searchedData: LiveData<ForecastEntity>
        get() = _searchedData

    private val _errorData: MutableLiveData<Result.Error> = MutableLiveData()
    val errorData: LiveData<Result.Error>
        get() = _errorData

    val data = listOf(searchedData, currentData)

    @SuppressLint("MissingPermission")
    private val location =
        (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).getLastKnownLocation(
            LocationManager.GPS_PROVIDER
        )

    fun getDataByCity(city: String) {
        CoroutineScope(IO).launch {
            when (val result: Result<ForecastEntity> = repository.getDataByCity(city)) {
                is Result.Success -> {
                    val newList = mutableListOf<Forecast>()
                    result.let {
                        for (index in 0..7) {
                            newList.add(result.data.list[index])
                        }
                        withContext(Main) {
                            _currentData.postValue(result.data.copy(list = newList))
                        }
                    }
                }
                is Result.Error -> withContext(Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }

    fun getDataByCoordinates() {
        CoroutineScope(IO).launch {
            when (val result: Result<ForecastEntity> = repository.getDataByCoordinates(
                location.latitude.toFloat(),
                location.longitude.toFloat()
            )) {
                is Result.Success -> {
                    val newList = mutableListOf<Forecast>()
                    result.let {
                        for (index in 0..7) {
                            newList.add(result.data.list[index])
                        }
                        withContext(Main) {
                            _currentData.postValue(result.data.copy(list = newList))
                        }
                    }
                }
                is Result.Error -> withContext(Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }

    private fun initializeData(lat: Float, lon: Float) {
        CoroutineScope(IO).launch {
            repository.getDataByCoordinates(
                lat, lon
            )
        }
    }
}
