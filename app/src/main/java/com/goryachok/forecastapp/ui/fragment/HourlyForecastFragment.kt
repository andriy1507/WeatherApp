package com.goryachok.forecastapp.ui.fragment

import androidx.fragment.app.Fragment

class HourlyForecastFragment : Fragment() {

//    private val viewModel by lazy { ViewModelProvider(this).get(HourlyForecastViewModel::class.java) }
//    private lateinit var adapter: HourlyForecastAdapter
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.hourly_forecast_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        adapter =
//            HourlyForecastAdapter(viewModel.data.value!!.list)
//        hourlyForecast_recyclerView.layoutManager = LinearLayoutManager(context)
//        hourlyForecast_recyclerView.adapter = adapter
//        viewModel.data.observe(viewLifecycleOwner, Observer {
//            adapter.update(it.list)
//            hourlyForecast_cityName_textView.text =
//                getString(R.string.location_template, it.city.name, it.city.country)
//        })
//    }
}