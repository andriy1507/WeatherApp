package com.goryachok.core_ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    abstract fun setupDependencies()

    abstract fun onSearchRequest()

    abstract fun onLocationRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
    }
}