package com.example.find

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityFindBinding
import com.example.login.Login
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Find : AppCompatActivity() {
    private lateinit var viewBinding: ActivityFindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val tabTitle = arrayOf("아이디 찾기","비밀번호 찾기")
        val VpFind = VPAdapterfind(this)
        viewBinding.vpAdapterfind.adapter = VpFind

        TabLayoutMediator(viewBinding.findtab, viewBinding.vpAdapterfind){
                tab, position -> tab.text = tabTitle[position]
        }.attach()

        viewBinding.findtab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0->viewBinding.title.setText("  아이디 찾기")
                    else -> viewBinding.title.setText("비밀번호 찾기")

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        viewBinding.findtab.setTabTextColors(Color.rgb(29,45,105), Color.rgb(255,255,255))

        viewBinding.login.setOnClickListener(){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

    }
}