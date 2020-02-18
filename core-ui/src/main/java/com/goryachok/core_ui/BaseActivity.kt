package com.goryachok.core_ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData

abstract class BaseActivity : AppCompatActivity() {

    val error = MutableLiveData<Pair<Boolean, Exception?>>()

    abstract fun setupDependencies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
    }

    abstract fun animateColors(temp: Int)
}