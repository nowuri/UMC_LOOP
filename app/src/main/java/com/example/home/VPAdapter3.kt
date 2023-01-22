package com.example.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VPAdapter3(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int =5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->HomeSeoul()
            1->HomeGyeonggi()
            2->HomeChung()
            3->HomeGang()
            else->HomeJeju()
        }
    }

}