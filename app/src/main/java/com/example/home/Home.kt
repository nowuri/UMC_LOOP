package com.example.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.RecommedVPAdpager
import com.example.interested.MainActivity_interest
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityHomeBinding
import com.example.login.Login
import com.example.mypage.MyPage_MainActivity
import com.example.network.*
import com.example.qna.Question
import com.example.search.Search
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Response

// 아직 유튜브를 구현안하신 것 같아 주석처리 해놓겠습니다!
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions

class Home : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding
    public val API_KEY = "AIzaSyBw2owx9ckx0xwCtDdO7Xz4Dp3MnelSuTE"
    public val VIDEO_ID = "0_OqbQArcGg&t=4s"

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
            val intent = Intent(this, MyPage_MainActivity::class.java)
            startActivity(intent)
        }
        viewBinding.login.setOnClickListener(){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }



        //val Info = HomeDataRequestBody("주거", "서울")
        val retrofitWork = RetrofitWork()
        retrofitWork.work()

        viewBinding.scrollview.fullScroll(ScrollView.FOCUS_DOWN)
        viewBinding.scrollview.fullScroll(ScrollView.FOCUS_UP)

    }

    class RetrofitWork(){
        fun work(){
            Log.e("정책 불러오기","시작")
            val service = RetrofitClient.emgMedService

            service.HomeDataGet()
                .enqueue(object: retrofit2.Callback<HomeDataResponseBody>{
                    override fun onResponse(call: Call<HomeDataResponseBody>, response: Response<HomeDataResponseBody>) {
                        if(response.isSuccessful){
                            val result = response.body()
                            Log.d("정책 불러오기 성공","$result")
                        }
                    }

                    override fun onFailure(call: Call<HomeDataResponseBody>, t: Throwable) {
                        Log.d("정책 불러오기 실패",t.message.toString())
                    }

                })
        }
    }

}