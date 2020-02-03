package com.goryachok.main

import android.os.Bundle
import com.goryachok.core.base.App
import com.goryachok.core_ui.base.MainActivity

class MainActivityImpl : MainActivity() {

    override fun setupDependencies() {
        (applicationContext as App).component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
    }
}