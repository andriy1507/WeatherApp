package com.goryachok.forecastapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.repository.Repository
import javax.inject.Inject

class SplashViewModel @Inject constructor() : RepoViewModel() {
    @Inject
    override lateinit var repository: Repository

    init {
        Log.d("DaggerDebug", javaClass.name)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SplashViewModel() as T
        }
    }
}