package com.example.foodreceipe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.foodreceipe.fragments.HomeFragment

class ViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
   private val mFragmentList: MutableList<Fragment> = ArrayList()
    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragmentList(fragment: Fragment){
        mFragmentList.add(fragment)
    }
}