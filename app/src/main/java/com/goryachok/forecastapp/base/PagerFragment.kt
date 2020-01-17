package com.goryachok.forecastapp.base

import androidx.fragment.app.Fragment

abstract class PagerFragment:Fragment() {
    abstract fun onSearchRequest(request:String)
    abstract fun onLocationRequest()
}