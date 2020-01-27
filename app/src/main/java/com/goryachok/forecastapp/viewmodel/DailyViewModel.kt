package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goryachok.forecastapp.base.getEveryEighth
import com.goryachok.forecastapp.model.domain.Forecast
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.model.local.Result
import com.goryachok.forecastapp.repository.BaseRepository
import com.goryachok.forecastapp.repository.ForecastRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DailyViewModel(context: Context) : MyViewModel() {

    private val repository: BaseRepository<ForecastEntity> by lazy { ForecastRepository(context) }

    private val _currentData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val currentData: LiveData<ForecastEntity>
        get() = _currentData

    private val _searchedData: MutableLiveData<ForecastEntity> = MutableLiveData()
    private val searchedData: LiveData<ForecastEntity>
        get() = _searchedData

    val data = listOf(searchedData, currentData)

    fun getDataByCity(city: String) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(Result.Loading)
            when (val result: Result<ForecastEntity> = repository.getDataByCity(city)) {
                is Result.Success -> {
                    val newList: List<Forecast> =
                        result.data.list.getEveryEighth()
                    withContext(Dispatchers.Main) {
                        _currentData.postValue(result.data.copy(list = newList))
                    }
//                    }
                }
                is Result.Error -> withContext(Dispatchers.Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }

    fun getDataByCoordinates(loc: Location) {
        CoroutineScope(Dispatchers.IO).launch {
            _loadData.postValue(Result.Loading)
            when (val result: Result<ForecastEntity> = repository.getDataByCoordinates(
                loc.latitude.toFloat(),
                loc.longitude.toFloat()
            )) {
                is Result.Success -> {
                    val newList = result.data.list.getEveryEighth()
                    withContext(Dispatchers.Main) {
                        _currentData.postValue(result.data.copy(list = newList))
                    }
//                    }
                }
                is Result.Error -> withContext(Dispatchers.Main) {
                    _errorData.postValue(result)
                }
            }
        }
    }
}