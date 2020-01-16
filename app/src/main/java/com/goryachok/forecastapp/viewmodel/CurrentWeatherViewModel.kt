package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.*
import com.goryachok.forecastapp.base.PagerModel
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.services.GeolocationListener
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

class CurrentWeatherViewModel : PagerModel(){
    @Inject
    override lateinit var repository: Repository

    private val _data = MutableLiveData<WeatherEntity>()

    val data: LiveData<WeatherEntity> = _data

    override fun getDataByCity(request: String) {
        liveData(IO) {
            emit(repository.getDataByCity(request,WeatherEntity::class))
            emitSource(data)
        }
    }

    override fun getDataByLocation() {
        val lon = GeolocationListener.geoLocation!!.longitude
        val lat = GeolocationListener.geoLocation!!.latitude
        liveData(IO) {
            emit(repository.getDataByCoordinates(lon.toFloat(), lat.toFloat(),WeatherEntity::class))
            emitSource(data)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrentWeatherViewModel() as T
        }
    }
}