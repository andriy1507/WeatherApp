package com.goryachok.forecastapp.base

import android.os.Bundle
import dagger.android.support.DaggerFragment

abstract class PagerFragment : DaggerFragment() {

    abstract fun onSearchRequest(request: String)

    abstract fun onLocationRequest()

    abstract fun setupDependencies()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupDependencies()
    }
}