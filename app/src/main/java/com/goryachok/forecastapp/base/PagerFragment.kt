package com.goryachok.forecastapp.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class PagerFragment : Fragment() {

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest()

    abstract fun setupDependencies()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupDependencies()
    }
}