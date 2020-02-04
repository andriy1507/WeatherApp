package com.goryachok.hourly

import android.location.Location
import android.os.Bundle
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.hourly.di.HourlyFragmentComponent
import javax.inject.Inject

class HourlyFragment : BaseFragment(R.layout.hourly_forecast_fragment) {

    @Inject
    lateinit var viewModel: HourlyViewModel

    override fun setupDependencies() {
        HourlyFragmentComponent.Initializer().init(this).inject(this)
    }

    override fun onSearchRequest(query: String) {
        viewModel.getDataByCity(query)
    }

    override fun onLocationRequest(location: Location) {
        viewModel.getCurrentLocationData(location)
    }

    companion object {
        private const val CITY_NAME_KEY = "city_name"

        fun newInstance() = HourlyFragment().apply {
            arguments = Bundle()
        }
    }
}