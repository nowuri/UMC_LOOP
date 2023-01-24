package com.example.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter2 (fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> Seoul()
            1-> Gyeonggi()
            2-> ChungJun()
            3-> GangGyeong()
            else-> Jeju()
        }
    }
}