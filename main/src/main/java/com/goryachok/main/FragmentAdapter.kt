package com.goryachok.main

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.goryachok.core_ui.base.BaseFragment

class FragmentAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = mutableListOf<BaseFragment>()

    fun setFragments(list: List<BaseFragment>) {
        fragments.addAll(list)
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size
}