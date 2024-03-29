package com.example.search

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home.Home
import com.example.home.HomeSearchData
import com.example.interested.R
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityMainSearchBinding
import com.example.login.Login
import com.example.mypage.MyPage_MainActivity
import com.example.network.RetrofitClient
import com.example.qna.Question
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class Search : AppCompatActivity() {
    private lateinit var viewBinding: com.example.interested.databinding.ActivityMainSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityMainSearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        //검색 부분
        val itemlist = resources.getStringArray(R.array.itemList)
        val adapter = ArrayAdapter(this,R.layout.spinner_list,itemlist)
        viewBinding.spinner2.adapter = adapter

        //setupSpinnerHandler()

        //검색어 입력
        var input: String =""
        var Rinput: String = ""
        var Finput: String = ""
        viewBinding.searchBtn.isEnabled = false

        viewBinding.searchinput.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                input = viewBinding.searchinput.text.toString()
                viewBinding.searchBtn.isEnabled = input.isNotEmpty()

                val words = input.split("\\s".toRegex()).toTypedArray()
                Log.e("words","${words.contentToString()}")
                Rinput = words[0]
                Finput = words[1]
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        //검색 버튼
        //엔터키 누르면 키보드는 내려가지만 검색 toast는 안됨
        //키보드 or 검색 결과 toast 중 둘 중 하나만 되는 상황
        //spinner와 searchBtn 버튼 괄호 바꾸기 spinner 안에 searchBtn이 들어가도록!

        viewBinding.spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0->{
                        Log.e("spinner",itemlist[p2])
//                        viewBinding.searchinput.setOnKeyListener { v, keyCode, event ->
//                            var handled = false
//                            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
//                                val inputMethodManager =
//                                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                                inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,
//                                    0)
//                                handled = true
//                                Toast.makeText(this@Search, input + "을/를 검색합니다", Toast.LENGTH_SHORT)
//                                    .show()
//                                RetrofitWork(input, "서울").work()
//                            }
//                            handled
//                        }
                        viewBinding.searchBtn.setOnClickListener(){
                            Toast.makeText(this@Search, input + "을/를 검색합니다.",Toast.LENGTH_SHORT).show()
                            Log.e("Input check","$input")



                            RetrofitWork(Finput,Rinput).work()
                        }

                        val regionBuilder = AlertDialog.Builder(this@Search)
                        regionBuilder.setTitle("지역별 검색 결과입니다")

                        var v1 = layoutInflater.inflate(R.layout.search_result,null)
                        regionBuilder.setView(v1)



                    }
                    1->{
                        Log.e("spinner",itemlist[1])
//                        viewBinding.searchinput.setOnKeyListener { v, keyCode, event ->
//                            var handled = false
//                            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
//                                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                                inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,0)
//                                handled = true
//                                Toast.makeText(this@Search,input+"을/를 검색합니다",Toast.LENGTH_SHORT).show()
//                                RetrofitWork2(input,"취업").work()
//                            }
//                            handled
//                        }
                        viewBinding.searchBtn.setOnClickListener(){
                            Toast.makeText(this@Search,"$Rinput 과 $Finput 을/를 검색합니다",Toast.LENGTH_SHORT).show()

                            RetrofitWork2(Rinput,Finput).work()
                        }

                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

//        viewBinding.searchBtn.setOnClickListener(){
//            //Toast.makeText(this, input+"를/을 검색합니다", Toast.LENGTH_SHORT).show()
//            viewBinding.spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
//                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                   when(p2){
//                       0->{
//                           viewBinding.searchinput.setOnKeyListener { v, keyCode, event ->
//                               var handled = false
//                               if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
//                                   val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                                   inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,0)
//                                   handled = true
//                                   Toast.makeText(this@Search,input+"을/를 검색합니다",Toast.LENGTH_SHORT).show()
//                                   RetrofitWork(input,"서울").work()
//                               }
//                               handled
//                           }
//                       }
//                       1->{
//                           viewBinding.searchinput.setOnKeyListener { v, keyCode, event ->
//                               var handled = false
//                               if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
//                                   val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                                   inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,0)
//                                   handled = true
//                                   Toast.makeText(this@Search,input+"을/를 검색합니다",Toast.LENGTH_SHORT).show()
//                                   RetrofitWork2(input,"취업").work()
//                               }
//                               handled
//                           }
//
//                       }
//                   }
//                }
//
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                    TODO("Not yet implemented")
//                }
//
//            }
//        }
//        viewBinding.searchinput.setOnKeyListener { v, keyCode, event ->
//            var handled = false
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
//                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                inputMethodManager.hideSoftInputFromWindow(viewBinding.searchinput.windowToken,0)
//                handled = true
//                Toast.makeText(this,input+"을/를 검색합니다",Toast.LENGTH_SHORT).show()
//            }
//            handled
//        }
        //로그인
        viewBinding.login.setOnClickListener(){
            val intent = Intent(this,SignUp2Activity::class.java)
            startActivity(intent)
        }

        val tabTitleArray = arrayOf(
            "취업", "창업","주거","금융","건강","기타"
        )

        val tabTitleArray2 = arrayOf(
            "서울","경기","충청 전라","강원 경상","제주"
        )

        //정책 어댑터
        val mainVpAdapter = VpAdapter(this)
        viewBinding.vpAdapter.adapter = mainVpAdapter

        TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
                tab, position-> tab.text = tabTitleArray[position]
        }.attach()

        //분야별, 지역별
        viewBinding.field.setOnClickListener(){
            viewBinding.field.setTypeface(null, Typeface.BOLD)
            viewBinding.region.setTypeface(null,Typeface.NORMAL)
            val mainVpAdapter = VpAdapter(this)
            viewBinding.vpAdapter.adapter = mainVpAdapter
            TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
                    tab, position-> tab.text = tabTitleArray[position]
            }.attach()
        }

        viewBinding.region.setOnClickListener(){
            viewBinding.region.setTypeface(null, Typeface.BOLD)
            viewBinding.field.setTypeface(null,Typeface.NORMAL)
            val mainVpAdapter2 = VpAdapter2(this)
            viewBinding.vpAdapter.adapter = mainVpAdapter2
            TabLayoutMediator(viewBinding.tablayout, viewBinding.vpAdapter){
                    tab, position-> tab.text = tabTitleArray2[position]
            }.attach()
        }
        viewBinding.tablayout.setTabTextColors(Color.rgb(29,45,105), Color.rgb(255,255,255))

        viewBinding.menuHome.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
        viewBinding.menuQna.setOnClickListener(){
            val intent = Intent(this,Question::class.java)
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

        //val field = PolicyFieldRequestBody("취업")
        //val retrofitWork = RetrofitWork(field)
        //retrofitWork.work()

        //var region = PolicyRegionRequestBody("제주")
        //val retrofitWork2 = RetrofitWork2(region)
        //retrofitWork2.work()
    }


    /////////////////이거 뭐지..?(레나)
    private fun setupSpinnerHandler(){
        viewBinding.spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }



    class RetrofitWork(private val keyword: String, private val region: String){
        var dataList: ArrayList<HomeSearchData> = arrayListOf()
        fun work(){
            val service = RetrofitClient.emgMedService

            service.regionSearch(keyword, region)
                .enqueue(object: retrofit2.Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("지역별 정책 검색하기 성공","$responsebody")

                            val jsonObject = response.body()?.getAsJsonObject("result")
                            val jsonObject2 = jsonObject?.getAsJsonObject("empsInfo")
                            val jsonArray = jsonObject2?.getAsJsonArray("emp")


                            if (jsonArray != null) {
                                var a: Int = 0
                                while(a <= jsonArray.size()-1){
                                    val Jsonfor = jsonArray[a].getAsJsonObject()
                                    val name = Jsonfor.get("polyBizSjnm").getAsString()
                                    val publicName = Jsonfor.get("cnsgNmor").getAsString()

                                    dataList.add(
                                        HomeSearchData(
                                            name,
                                            publicName
                                        )
                                    )
                                    a+=1
                                }
                            }
                        }
                        else {
                            val code = response.code()
                            Log.e("지역별 정책 검색하기 상태","$code")
                        }
                    }


                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("지역별 키워드 검색 실패", t.message.toString())
                    }

                })
        }
    }

    class RetrofitWork2(private val keyword: String, private val field: String){
        var dataList: ArrayList<HomeSearchData> = arrayListOf()
        fun work(){
            val service = RetrofitClient.emgMedService

            service.fieldSearch(keyword, field)
                .enqueue(object: retrofit2.Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("분야별 정책 검색하기 성공","$responsebody")

                            val jsonObject = response.body()?.getAsJsonObject("result")
                            val jsonObject2 = jsonObject?.getAsJsonObject("empsInfo")
                            val jsonArray = jsonObject2?.getAsJsonArray("emp")

                            if (jsonArray != null) {
                                var a: Int = 0
                                while(a <= jsonArray.size()-1){
                                    val Jsonfor = jsonArray[a].getAsJsonObject()
                                    val name = Jsonfor.get("polyBizSjnm").getAsString()
                                    val publicName = Jsonfor.get("cnsgNmor").getAsString()

                                    dataList.add(
                                        HomeSearchData(
                                            name,
                                            publicName
                                        )
                                    )
                                    a+=1
                                }
                            }
                        }
                        else {
                            val code = response.code()
                            Log.e("분야별 정책 검색하기 상태","$code")
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("분야별 키워드 검색 실패", t.message.toString())
                    }


                })
        }
    }
}

