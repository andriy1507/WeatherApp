package com.goryachok.forecastapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.forecastapp.ui.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.ui.fragment.DailyForecastFragment
import com.goryachok.forecastapp.ui.fragment.HourlyForecastFragment

class ForecastPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages: List<Fragment> = listOf(
        CurrentWeatherFragment(),
        HourlyForecastFragment(),
        DailyForecastFragment()
    )

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getCount(): Int = pages.size
}