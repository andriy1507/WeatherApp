package com.goryachok.forecastapp.ui.fragment

import androidx.fragment.app.Fragment

class DailyForecastFragment : Fragment() {

//    private val viewModel by lazy { ViewModelProvider(this).get(DailyForecastViewModel::class.java) }
//    private lateinit var adapter: DailyForecastAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.daily_forecast_fragment, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        adapter =
//            DailyForecastAdapter(viewModel.data.value!!.list)
//        dailyForecast_recyclerView.layoutManager = LinearLayoutManager(context)
//        dailyForecast_recyclerView.adapter = adapter
//        viewModel.data.observe(viewLifecycleOwner, Observer {
//            adapter.update(it.list)
//            dailyForecast_cityName_textView.text =
//                getString(R.string.location_template, it.city.name, it.city.country)
//        })
//    }
}