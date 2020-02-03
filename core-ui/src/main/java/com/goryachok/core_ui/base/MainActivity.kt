package com.goryachok.core_ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class MainActivity : AppCompatActivity() {

    abstract fun setupDependencies()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
    }
}