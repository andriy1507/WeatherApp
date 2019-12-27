package com.goryachok.forecastapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.forecastapp.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.fragment.DailyForecastFragment
import com.goryachok.forecastapp.fragment.HourlyForecastFragment

class ForecastPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val pages: List<Fragment> = listOf(
        CurrentWeatherFragment(),
        HourlyForecastFragment(),
        DailyForecastFragment()
    )

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getCount(): Int = pages.size
}