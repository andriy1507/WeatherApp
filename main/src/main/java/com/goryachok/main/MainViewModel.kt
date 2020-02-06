package com.goryachok.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import javax.inject.Inject

class MainViewModel(
    private val locationProvider: LocationProvider,
    private val connectivityListener: ConnectivityListener
) : ViewModel() {

    init {
        connectivityListener.apply {
            setOnConnectionAvailableCallback { connectionStatus.postValue(true) }
            setOnConnectionLostCallback { connectionStatus.postValue(false) }
        }
    }

    var requestCache = ""

    var locationCache: Location? = null

    val connectionStatus = MutableLiveData<Boolean>()

    fun setTaskForLocationProvider(func: (location: Location) -> Unit) {
        locationProvider.setTask(func)
    }

    fun startLocationProvider() {
        locationProvider.start()
    }

    fun stopLocationProvider() {
        locationProvider.stop()
    }

    fun startConnectivityListener() {
        connectivityListener.start()
    }

    fun stopConnectivityListener() {
        connectivityListener.stop()
    }

    fun isNetworkAvailable(): Boolean =
        connectivityListener.isNetworkAvailable()

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val locationProvider: LocationProvider,
        private val connectivityListener: ConnectivityListener
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(locationProvider, connectivityListener) as T
        }
    }

}