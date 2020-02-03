package com.goryachok.core_util

import com.goryachok.core.base.ConnectivityListener
import com.goryachok.core.base.LocationProvider
import com.goryachok.core.base.viewmodels.MainViewModel
import javax.inject.Inject

class MainViewModelImpl : MainViewModel() {

    init {
        connectivityListener.apply {
            setOnConnectionAvailableCallback { connectionStatus.postValue(true) }
            setOnConnectionLostCallback { connectionStatus.postValue(false) }
        }
    }

    @Inject
    lateinit var locationProvider: LocationProvider

    @Inject
    lateinit var connectivityListener: ConnectivityListener
}