package com.goryachok.forecastapp.view.fragment

import androidx.fragment.app.Fragment

abstract class MyFragment: Fragment() {

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest()
}
