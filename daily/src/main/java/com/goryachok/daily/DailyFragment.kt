package com.goryachok.daily

import android.location.Location
import android.os.Bundle
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.daily.di.DailyFragmentComponent
import javax.inject.Inject

class DailyFragment : BaseFragment(R.layout.daily_forecast_fragment) {

    @Inject
    lateinit var viewModel: DailyViewModel

    override fun setupDependencies() {
        DailyFragmentComponent.Initializer().init(this).inject(this)
    }

    override fun onSearchRequest(query: String) {
        viewModel.getDataByCity(query)
    }

    override fun onLocationRequest(location: Location) {
        viewModel.getCurrentLocationData(location)
    }

    companion object {

        private const val CITY_NAME_KEY = "city_name"

        fun newInstance() = DailyFragment().apply {
            arguments = Bundle()
        }
    }
}