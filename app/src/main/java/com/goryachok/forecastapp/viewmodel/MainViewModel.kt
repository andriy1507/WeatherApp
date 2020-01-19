package com.goryachok.forecastapp.viewmodel

import androidx.lifecycle.ViewModel
import com.goryachok.forecastapp.di.repository.DaggerRepositoryComponent
import com.goryachok.forecastapp.repository.Repository
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    init {
        DaggerRepositoryComponent.create().inject(this)
    }
}