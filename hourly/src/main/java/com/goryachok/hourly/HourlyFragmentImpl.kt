package com.goryachok.hourly

import com.goryachok.core.base.App
import com.goryachok.core_ui.base.HourlyFragment

class HourlyFragmentImpl : HourlyFragment(R.layout.hourly_forecast_fragment) {
    override fun setupDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun onSearchRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationRequest() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}