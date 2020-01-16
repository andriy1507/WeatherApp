package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData

import com.goryachok.forecastapp.base.PagerModel
import com.goryachok.forecastapp.model.domain.ForecastEntity
import com.goryachok.forecastapp.repository.Repository
import com.goryachok.forecastapp.services.GeolocationListener
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HourlyForecastViewModel : PagerModel() {
    @Inject
    override lateinit var repository: Repository

    val data: LiveData<ForecastEntity>

    init {
        val lon = GeolocationListener.geoLocation!!.longitude
        val lat = GeolocationListener.geoLocation!!.latitude
        data = liveData {
            emit(
                repository.getDataByCoordinates(
                    type = ForecastEntity::class,
                    lon = lon.toFloat(),
                    lat = lat.toFloat()
                )
            )
        }
    }

    override fun getDataByCity(request: String) {
        liveData(Dispatchers.IO) {
            emit(repository.getDataByCity(request, ForecastEntity::class))
            emitSource(data)
        }
    }

    override fun getDataByLocation() {
        val lon = GeolocationListener.geoLocation!!.longitude
        val lat = GeolocationListener.geoLocation!!.latitude
        liveData(Dispatchers.IO) {
            emit(
                repository.getDataByCoordinates(
                    lon.toFloat(),
                    lat.toFloat(),
                    ForecastEntity::class
                )
            )
            emitSource(data)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HourlyForecastViewModel() as T
        }
    }
}