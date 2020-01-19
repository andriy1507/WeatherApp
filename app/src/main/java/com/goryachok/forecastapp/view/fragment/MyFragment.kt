package com.goryachok.forecastapp.view.fragment

import androidx.fragment.app.Fragment

abstract class MyFragment: Fragment() {

    init {
        this.setupDependencies()
    }

    abstract fun setupDependencies()

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest()
}
