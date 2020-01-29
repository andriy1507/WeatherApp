package com.goryachok.forecastapp.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.ConnectivityListener
import com.goryachok.forecastapp.LocationProvider
import javax.inject.Inject

class MainViewModel @Inject constructor(
    val locationProvider: LocationProvider,
    val connectivityListener: ConnectivityListener
) : ViewModel() {

    init {
        connectivityListener.apply {
            setOnConnectionAvailableCallback { connectionStatus.postValue(true) }
            setOnConnectionLostCallback { connectionStatus.postValue(false) }
        }

    }

    val connectionStatus = MutableLiveData<Boolean>()

    var requestCache = ""

    var locationCache: Location? = null

}