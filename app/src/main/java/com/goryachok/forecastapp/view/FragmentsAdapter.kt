package com.goryachok.forecastapp.view

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.forecastapp.view.fragment.MyFragment

class FragmentsAdapter(manager: FragmentManager) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = mutableListOf<MyFragment>()

    override fun getItem(position: Int): MyFragment = fragments[position]

    override fun getCount(): Int = fragments.size

    fun addFragments(fragmentList: List<MyFragment>) {
        fragments.addAll(fragmentList)
        notifyDataSetChanged()
    }
}