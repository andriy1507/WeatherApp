package com.goryachok.core.base.viewmodels

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class MainViewModel : ViewModel() {

    val connectionStatus = MutableLiveData<Boolean>()

    var requestCache = ""

    var locationCache: Location? = null
}