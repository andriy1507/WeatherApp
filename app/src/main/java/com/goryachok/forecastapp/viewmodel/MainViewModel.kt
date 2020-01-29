package com.goryachok.forecastapp.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.ConnectivityListener
import com.goryachok.forecastapp.LocationProvider

class MainViewModel(context: Context) : ViewModel() {

    val locationProvider by lazy { LocationProvider(context) }

    val connectivityListener: ConnectivityListener by lazy {
        ConnectivityListener(context).apply {
            setOnConnectionAvailableCallback { connectionStatus.postValue(true) }
            setOnConnectionLostCallback { connectionStatus.postValue(false) }
        }
    }

    val connectionStatus = MutableLiveData<Boolean>()

    var requestCache = ""

    var locationCache: Location? = null

}