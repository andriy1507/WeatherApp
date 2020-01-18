package com.goryachok.forecastapp.view.adapters

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.forecastapp.base.PagerFragment
import javax.inject.Inject

class ForecastPagerAdapter @Inject constructor(
    manager: FragmentManager,
    val pages: List<PagerFragment>
) :
    FragmentPagerAdapter(
        manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    override fun getItem(position: Int): PagerFragment = pages[position]

    override fun getCount(): Int = pages.size
}