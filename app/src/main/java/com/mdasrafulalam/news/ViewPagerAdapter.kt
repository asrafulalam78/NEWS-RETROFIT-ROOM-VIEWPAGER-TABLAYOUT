package com.mdasrafulalam.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


 class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val NUM_TABS = 7
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AllNewsFragment()
            1 -> return BusinessFragment()
            2 -> return EntertainmentFragment()
            3 -> return ScienceFragment()
            4 -> return SportsFragment()
            5 -> return TechnologyFragment()
            else-> return HealthFragment()
        }
    }
}