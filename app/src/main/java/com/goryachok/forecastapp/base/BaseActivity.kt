package com.goryachok.forecastapp.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity(){
    protected abstract fun setupDependencies()
}