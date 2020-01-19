package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.repository.Repository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    val repository: Repository,
    val context: Context
) : ViewModel() {

    private val locationManager: LocationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    init {
        DaggerRepositoryComponent.create().inject(this)
    }

    fun initialize() {
//        repository.initializeData()
    }
}