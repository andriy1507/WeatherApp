package com.goryachok.core_util.viewmodels

import com.goryachok.core.business.ConnectivityListener
import com.goryachok.core.business.LocationProvider
import com.goryachok.core.viewmodels.MainViewModel

class MainViewModelImpl(
    private val locationProvider: LocationProvider,
    private val connectivityListener: ConnectivityListener
) : MainViewModel() {

    init {
        connectivityListener.apply {
            setOnConnectionAvailableCallback { connectionStatus.postValue(true) }
            setOnConnectionLostCallback { connectionStatus.postValue(false) }
        }
    }
}