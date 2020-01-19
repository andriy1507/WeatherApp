package com.goryachok.forecastapp.view

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.forecastapp.view.fragment.MyFragment

class FragmentsAdapter(manager: FragmentManager, val pages: List<MyFragment>) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): MyFragment = pages[position]

    override fun getCount(): Int = pages.size
}