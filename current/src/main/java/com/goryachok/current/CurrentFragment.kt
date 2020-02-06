package com.goryachok.current

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.goryachok.core_ui.BaseActivity
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.current.di.CurrentFragmentComponent
import kotlinx.android.synthetic.main.current_weather_fragment.*
import javax.inject.Inject

class CurrentFragment private constructor() : BaseFragment(R.layout.current_weather_fragment) {

    @Inject
    lateinit var viewModel: CurrentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            errorData.observe(viewLifecycleOwner, Observer { error ->
                currentLoadingProgressBar.visibility = View.GONE
                (activity as? BaseActivity)?.error?.postValue(Pair(true, error.exception))
            })
            loadData.observe(viewLifecycleOwner, Observer {
                (activity as? BaseActivity)?.error?.postValue(Pair(false, null))
                currentLoadingProgressBar.visibility = View.VISIBLE
            })
            data.forEach { liveData ->
                liveData.observe(viewLifecycleOwner, Observer {
                    curLocation.text = it.city
                    currentTemp_textView.text = getString(R.string.temperature_template, it.temp)
                    curWindSpeed_textView.text = getString(R.string.windSpeed_template, it.windSpd)
                    curWindDir_textView.text = convertDegreesToDirection(it.windDir)
                    curHumid_textView.text = getString(R.string.humidity_template, it.humidity)
                    curPress_textView.text = getString(R.string.pressure_template, it.pressure)
                    currentLoadingProgressBar.visibility = View.GONE
                })
            }
        }
    }

    override fun setupDependencies() {
        CurrentFragmentComponent.Initializer().init(this).inject(this)
    }

    override fun onSearchRequest(query: String) {
        viewModel.getDataByCity(query)
    }

    override fun onLocationRequest(location: Location) {
        viewModel.getCurrentLocationData(location)
    }

    companion object {

        private const val CITY_NAME_KEY = "city_name"

        fun newInstance() = CurrentFragment().apply {
            arguments = Bundle()
        }
    }
}