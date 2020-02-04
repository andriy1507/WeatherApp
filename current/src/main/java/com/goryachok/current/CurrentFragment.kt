package com.goryachok.current

import android.location.Location
import android.os.Bundle
import com.goryachok.core_ui.base.BaseFragment

class CurrentFragment : BaseFragment(R.layout.current_weather_fragment) {

    override fun setupDependencies() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSearchRequest(query: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationRequest(location: Location) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val CITY_NAME_KEY = "city_name"

        fun newInstance() = CurrentFragment().apply {
            arguments = Bundle()
        }
    }
}