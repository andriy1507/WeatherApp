package com.goryachok.forecastapp.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.goryachok.forecastapp.BuildConfig
import com.goryachok.forecastapp.R
import com.goryachok.forecastapp.di.viewmodel.DaggerViewModelComponent
import com.goryachok.forecastapp.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(SplashViewModel::class.java)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    init {
        DaggerViewModelComponent.create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.data.observe(this, Observer {
            Log.d(BuildConfig.TAG, it.toString())
        })

        startActivity(Intent(this, MainActivity::class.java))
    }
}