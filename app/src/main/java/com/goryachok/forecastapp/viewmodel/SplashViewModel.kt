package com.goryachok.forecastapp.viewmodel

import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.model.domain.WeatherEntity
import com.goryachok.forecastapp.repository.Repository
import javax.inject.Inject

class SplashViewModel : ViewModel() {

    private val _data: MutableLiveData<WeatherEntity> = MutableLiveData()
    val data: LiveData<WeatherEntity>
        get() = _data

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var locationManager: LocationManager

    init {
        DaggerRepositoryComponent.create().inject(this)
    }
}