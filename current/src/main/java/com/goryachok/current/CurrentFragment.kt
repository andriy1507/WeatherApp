package com.goryachok.current

import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.goryachok.core_ui.base.BaseFragment
import com.goryachok.current.di.CurrentFragmentComponent
import kotlinx.android.synthetic.main.current_weather_fragment.*
import javax.inject.Inject
import kotlin.math.roundToInt

class CurrentFragment : BaseFragment(R.layout.current_weather_fragment) {

    @Inject
    lateinit var viewModel: CurrentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.data.forEach { liveData ->
            liveData.observe(viewLifecycleOwner, Observer {
                curLocation.text = it.city
                currentTemp_textView.text = it.temp.roundToInt().toString()
                curWindSpeed_textView.text = getString(R.string.windSpeed_template, it.windSpd)
                curWindDir_textView.text = convertDegreesToDirection(it.windDir)
                curHumid_textView.text = getString(R.string.humidity_template, it.humidity)
                curPress_textView.text = getString(R.string.pressure_template, it.pressure)
            })
            currentLoadingProgressBar.visibility = View.GONE
        }
        viewModel.loadData.observe(viewLifecycleOwner, Observer {
            currentLoadingProgressBar.visibility = View.VISIBLE
        })
        viewModel.errorData.observe(viewLifecycleOwner, Observer { error ->
            currentLoadingProgressBar.visibility = View.GONE
            this@CurrentFragment.context?.let {
                AlertDialog.Builder(it).setTitle(getString(R.string.error))
                    .setMessage(error.exception.message)
                    .setPositiveButton(getString(R.string.close_button)) { dialog, _ ->
                        dialog.dismiss()
                    }
            }
        })
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