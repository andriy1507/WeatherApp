package com.goryachok.forecastapp.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.forecastapp.base.PagerFragment
import com.goryachok.forecastapp.view.fragment.CurrentWeatherFragment
import com.goryachok.forecastapp.view.fragment.DailyForecastFragment
import com.goryachok.forecastapp.view.fragment.HourlyForecastFragment

class ForecastPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val pages: List<PagerFragment> = listOf(
        CurrentWeatherFragment(),
        HourlyForecastFragment(),
        DailyForecastFragment()
    )

    override fun getItem(position: Int): Fragment = pages[position]

    override fun getCount(): Int = pages.size
}