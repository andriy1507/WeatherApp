package com.goryachok.current

import android.location.Location
import com.goryachok.core.App
import com.goryachok.core_ui.CurrentFragment

class CurrentFragmentImpl : CurrentFragment(R.layout.current_weather_fragment) {

    override fun setupDependencies() {
        (activity?.applicationContext as App).component.inject(this)
    }

    override fun onSearchRequest(query: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationRequest(location: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}