package com.example.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.RecommedVPAdpager
import com.example.interested.R
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityHomeBinding
import com.example.qna.Question
import com.example.search.Search
import com.google.android.material.tabs.TabLayoutMediator

class Home : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding

    inner class PagerRunnable: Runnable{
        var currentPage = 0
        fun setPage(){
            if(currentPage == 3)
                currentPage = 0
            viewBinding.recommendVPAdapter.setCurrentItem(currentPage, true)
            currentPage += 1
        }
        val handler = Handler(Looper.getMainLooper()){
            setPage()
            true
        }
        override fun run() {
            while(true){
                try{
                    Thread.sleep(2000)
                    handler.sendEmptyMessage(0)
                }
                catch(e: InterruptedException){
                    Log.d("interupt","interup발생")
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val tabTitle = arrayOf("서울","경기","충청 전라","강원 경상","제주")
        val HomeAdapter = VPAdapter3(this)
        viewBinding.vpAdapter3.adapter = HomeAdapter

        TabLayoutMediator(viewBinding.tab, viewBinding.vpAdapter3){
                tab, position -> tab.text = tabTitle[position]
        }.attach()

        viewBinding.tab.setTabTextColors(Color.rgb(29,45,105), Color.rgb(255,255,255))

        val recommendAdapter = RecommedVPAdpager(this)
        recommendAdapter.addFragment(RecommendFragment())
        recommendAdapter.addFragment(RecommendFragment2())
        recommendAdapter.addFragment(RecommendFragment3())

        viewBinding.recommendVPAdapter.adapter = recommendAdapter
        viewBinding.recommendVPAdapter.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        val child = viewBinding.recommendVPAdapter.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER

        var thread = Thread(PagerRunnable())
        thread.start()

        val dotsIndicator = viewBinding.dotindicator
        dotsIndicator.attachTo(viewBinding.recommendVPAdapter)

        viewBinding.menuSearch.setOnClickListener(){
            val intent = Intent(this, Search::class.java)
            startActivity(intent)
        }
        viewBinding.menuQa.setOnClickListener(){
            val intent = Intent(this, Question::class.java)
            startActivity(intent)
        }
        viewBinding.menuProfile.setOnClickListener(){
            Toast.makeText(this,"프로필로 이동합니다.", Toast.LENGTH_SHORT).show()
        }
        viewBinding.login.setOnClickListener(){
            val intent = Intent(this, SignUp2Activity::class.java)
            startActivity(intent)
        }
    }
}