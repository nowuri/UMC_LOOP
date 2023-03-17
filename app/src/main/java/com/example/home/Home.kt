package com.example.home

// 아직 유튜브를 구현안하신 것 같아 주석처리 해놓겠습니다!
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.RecommedVPAdpager
import com.example.interested.databinding.ActivityHomeBinding
import com.example.login.Login
import com.example.mypage.MyPage_MainActivity
import com.example.network.*
import com.example.qna.Question
import com.example.search.Search
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


class Home : AppCompatActivity() {
    private lateinit var viewBinding: ActivityHomeBinding
    public val API_KEY = "AIzaSyBw2owx9ckx0xwCtDdO7Xz4Dp3MnelSuTE"
    public val VIDEO_ID = "0_OqbQArcGg&t=4s"

    val jsonObject = JSONObject("{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJuYW1lIjoiam9vbiJ9LCJpYXQiOjE2NzUzNTQ3OTh9.MaPPaQjlXqgDR6P84mO2UNj8Oi6lvtUsljGEJZxbuc8\"}")
    val data = token(
        jsonObject.getString("token")
    )
    var datalist = arrayListOf<Homedata>()
    val mAdapter = HomeAdapter1(this, datalist)
    val mAdapter2 = HomeSeoul()

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

        //지역별 정책 데이터 가져오기 (일단, 서울만 불러오도록 해봤음)
        val region = "서울"
        RetrofitWork(region).work()

        val tabTitle = arrayOf("서울","경기","충청 전라","강원 경상","제주")
        val HomeAdapter = VPAdapter3(this)
        viewBinding.vpAdapter3.adapter = HomeAdapter

        TabLayoutMediator(viewBinding.tab, viewBinding.vpAdapter3){
                tab, position -> tab.text = tabTitle[position]
        }.attach()

        //지역별 정책 데이터 가져오기 (일단, 서울만 불러오도록 해봤음)
        RetrofitWork(region).work()

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

        viewBinding.scrollview.fullScroll(ScrollView.FOCUS_DOWN)
        viewBinding.scrollview.fullScroll(ScrollView.FOCUS_UP)

        //val info = PolicyRegionSearchRequestBody("청년", "서울")
        //RetrofitWork2(info).work()

        //val policyID = PolicyDetailRequestBody("R2023080504223")
        //RetrofitWork3(policyID).work()

    }
    inner class RetrofitWork(private val region: String){
        fun work(){
            Log.e("정책 불러오기","시작")
            val service = RetrofitClient.emgMedService

            service.HomeDataGet(region)
                .enqueue(object : retrofit2.Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("정책 불러오기 성공","$responsebody")

                            val jsonArray = response.body()?.getAsJsonArray("result")
                            Log.e("정책 array","$jsonArray")


                            if (jsonArray != null) {
                                //for(i: Int in 0..jsonArray.                                                                                                                     size()){
                                    val Jsonfor = jsonArray[0].getAsJsonObject()
                                    val policyname = Jsonfor.get("policyName").getAsString()
                                    val department = Jsonfor.get("department").getAsString()
                                    Log.e("정책 이름, 정책 부서", "$policyname $department")

                                    datalist.add(
                                        Homedata(
                                            policyname,
                                            department
                                        )
                                    )
                                mAdapter.setList(datalist)
                                mAdapter2.dataList = datalist
                                mAdapter2.setList(datalist)

                                Log.e("정책 datalist","$datalist")

                                //val dto = Homedata(name = policyname, publicName = department)
                                //}
                            }
                        }
                        else {
                            val code = response.code()
                            Log.e("정책 불러오기 상태","$code")
                        }
                    }


                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.e("정책 불러오기 실패",t.message.toString())
                    }
                })
        }


    }
}

