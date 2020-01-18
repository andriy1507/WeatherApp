package com.goryachok.forecastapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: SplashViewModel


    lateinit var viewModelFactory:ViewModelProvider.Factory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel = ViewModelProvider(this,viewModelFactory).get(SplashViewModel::class.java)

        viewModel.data.observe(this, Observer {
            Log.d(BuildConfig.TAG, it.city)
        })
    }
}