package com.goryachok.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.goryachok.core.App
import com.goryachok.core.viewmodels.MainViewModel
import com.goryachok.core.viewmodels.factories.MainViewModelFactory
import com.goryachok.core_ui.MainActivity
import javax.inject.Inject

class MainActivityImpl : MainActivity() {

    //TODO rebuild ViewModel injection

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, factory).get(
            MainViewModel::class.java
        )
    }

    @Inject
    lateinit var factory: MainViewModelFactory

    override fun setupDependencies() {
        (applicationContext as App).component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
    }
}