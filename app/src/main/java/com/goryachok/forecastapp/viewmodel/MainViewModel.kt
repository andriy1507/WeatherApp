package com.goryachok.forecastapp.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var requestCache = ""

    var locationCache: Location? = null
}