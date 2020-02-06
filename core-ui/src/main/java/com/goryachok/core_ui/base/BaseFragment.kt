package com.goryachok.core_ui.base

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract fun setupDependencies()

    abstract fun onSearchRequest(query: String)

    abstract fun onLocationRequest(location: Location)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
    }
}