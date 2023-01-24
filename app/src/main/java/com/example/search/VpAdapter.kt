package com.example.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->WorkFragment()
            1->FoundFragment()
            2->LiveFragment()
            3->MoneyFragment()
            4->HealthFragment()
            5->EtcFragment()
            else->WorkFragment()
        }
    }


}