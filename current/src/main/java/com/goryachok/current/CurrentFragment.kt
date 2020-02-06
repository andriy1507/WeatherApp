package com.goryachok.current

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.goryachok.core_ui.BaseActivity
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.current.di.CurrentFragmentComponent
import kotlinx.android.synthetic.main.current_weather_fragment.*
import javax.inject.Inject
import kotlin.math.roundToInt

class CurrentFragment private constructor() : BaseFragment(R.layout.current_weather_fragment) {

    @Inject
    lateinit var viewModel: CurrentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            errorData.observe(viewLifecycleOwner, Observer { error ->
                error_textView.text = error.exception.message
                currentLoadingProgressBar.visibility = View.GONE
                contentGroup.visibility = View.GONE
                errorGroup.visibility = View.VISIBLE
            })
            loadData.observe(viewLifecycleOwner, Observer {
                errorGroup.visibility = View.GONE
                currentLoadingProgressBar.visibility = View.VISIBLE
            })
            data.forEach { liveData ->
                liveData.observe(viewLifecycleOwner, Observer {
                    errorGroup.visibility = View.GONE
                    contentGroup.visibility = View.VISIBLE
                    curLocation.text = it.city
                    currentTemp_textView.text = it.temp.roundToInt().toString()
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