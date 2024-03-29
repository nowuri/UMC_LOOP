package com.example.interested

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.location.Geocoder.isPresent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.auth0.jwt.exceptions.TokenExpiredException
import com.bumptech.glide.load.PreferredColorSpace
import com.example.home.Home
import com.example.interested.databinding.ActivityMainBinding
import com.example.login.Login.Companion.prefs
import com.example.network.*
import com.example.search.Search
import retrofit2.Retrofit
import com.google.gson.JsonArray
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Route
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalArgumentException
import java.security.SignatureException
import java.sql.Types.NULL


class MainActivity_interest : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding


    var num = 0
    var l =0; var t=0; var m=0; var w=0; var e=0; var f=0; var h=0; var etc=0
    var possible=-1

    var ID4 : String = ""
    var pw4 : String = ""
    var Name4: String = ""
    var tel4 : String = ""
    var birth4: String = ""
    var address4: String = ""
    var gettoken: String =""

    var checkbox_status_sms4: String = ""
    var checkbox_status_kkt4: String = ""
    var checkbox_status_info4: String = ""

    val jsonInterest = JSONArray()
    val jsonUnInterest = JSONArray()
    val interest = ArrayList<String>()
    val uninterest = arrayListOf(
        //취업
        "004001","004001001","004001002","004001003","004001004",
        //창업
        "004002","004002001","004002002","004002003",
        //금융
        "004003001","004003003",
        //주거
        "004003","004003002",
        //건강
        "004004","004004001",
        //문화
        "004004002",
        //기타
        "004005","004005001","004005002","004005003",
        //코로나
        "004006","004006001","004006002","004006003","004006004","004006006","004006005"

    )

//    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJuYW1lIjoiam9vbiJ9LCJpYXQiOjE2NzUzNTQ3OTh9.MaPPaQjlXqgDR6P84mO2UNj8Oi6lvtUsljGEJZxbuc8"
//    val jsonObject = JSONObject("{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJuYW1lIjoiam9vbiJ9LCJpYXQiOjE2NzUzNTQ3OTh9.MaPPaQjlXqgDR6P84mO2UNj8Oi6lvtUsljGEJZxbuc8\"}")
//    val data = token(
//        jsonObject.getString("token")
//    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Thread{
            var i=0
            while(true){
                i++
                Log.d("Thread: ",i.toString())
                if(num > 0){
                    viewBinding.finish.setBackgroundResource(R.drawable.changed_btn)
                    viewBinding.finish.setTextColor(Color.parseColor("#ffffff"))
                    possible = 1
                }
                else{
                    viewBinding.finish.setBackgroundResource(R.drawable.btn_style)
                    viewBinding.finish.setTextColor(Color.parseColor("#1D2D69"))
                    possible = 0
                }
                Thread.sleep(100)
            }
        }.start()

        viewBinding.back.setOnClickListener(){
//            이전 화면으로 돌아갈 수 있도록 함
            val intent = Intent(this, SignUp3Activity::class.java)
            startActivity(intent)
            Toast.makeText(this@MainActivity_interest,"뒤로 돌아가기",Toast.LENGTH_SHORT).show()
        }

        //여기서부터는 관심분야 선택했을 때 일어날 activity
        //홀수번 눌렀을 때: 남색으로 바뀜, 짝수번 눌렀을 때: 흰색으로 바뀜
        viewBinding.live.setOnClickListener(){
            l++
            if(l % 2 == 1){
                viewBinding.live.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.live.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004003002")) {
                    uninterest.remove("004003002")
                    uninterest.remove("004003")
                }
                interest.add("004003002")
                interest.add("004003")
            }
            else{
                viewBinding.live.setBackgroundResource(R.drawable.btn_style)
                viewBinding.live.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004003002")){
                    interest.remove("004003002")
                    interest.remove("004003")
                }
                uninterest.add("004003002")
                uninterest.add("004003")
            }
        }

        viewBinding.transport.setOnClickListener(){
            t++
            if(t % 2 == 1){
                viewBinding.transport.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.transport.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004004002"))
                    uninterest.remove("004004002")
                interest.add("004004002")
            }
            else{
                viewBinding.transport.setBackgroundResource(R.drawable.btn_style)
                viewBinding.transport.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004004002"))
                    interest.remove("004004002")
                uninterest.add("004004002")
            }
        }

        viewBinding.money.setOnClickListener(){
            m++
            if(m % 2 == 1){
                viewBinding.money.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.money.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004003001")) {
                    uninterest.remove("004003001")
                    uninterest.remove("004003003")
                }
                interest.add("004003001")
                interest.add("004003003")
            }
            else{
                viewBinding.money.setBackgroundResource(R.drawable.btn_style)
                viewBinding.money.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004003001")) {
                    interest.remove("004003001")
                    interest.remove("004003003")
                }
                uninterest.add("004003001")
                uninterest.add("004003003")
            }
        }

        viewBinding.work.setOnClickListener(){
            w++
            if(w % 2 == 1){
                viewBinding.work.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.work.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004001")) {
                    uninterest.remove("004001")
                    uninterest.remove("004001001")
                    uninterest.remove("004001002")
                    uninterest.remove("004001003")
                    uninterest.remove("004001004")
                }
                interest.add("004001")
                interest.add("004001001")
                interest.add("004001002")
                interest.add("004001003")
                interest.add("004001004")
            }
            else{
                viewBinding.work.setBackgroundResource(R.drawable.btn_style)
                viewBinding.work.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004001")) {
                    interest.remove("004001")
                    interest.remove("004001001")
                    interest.remove("004001002")
                    interest.remove("004001003")
                    interest.remove("004001004")
                }
                uninterest.add("004001")
                uninterest.add("004001001")
                uninterest.add("004001002")
                uninterest.add("004001003")
                uninterest.add("004001004")
            }
        }

        viewBinding.edu.setOnClickListener(){
            e++
            if(e % 2 == 1){
                viewBinding.edu.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.edu.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004006")) {
                    uninterest.remove("004006")
                    uninterest.remove("004006001")
                    uninterest.remove("004006002")
                    uninterest.remove("004006003")
                    uninterest.remove("004006004")
                    uninterest.remove("004006005")
                    uninterest.remove("004006006")
                }
                interest.add("004006")
                interest.add("004006001")
                interest.add("004006002")
                interest.add("004006003")
                interest.add("004006004")
                interest.add("004006005")
                interest.add("004006006")
            }
            else{
                viewBinding.edu.setBackgroundResource(R.drawable.btn_style)
                viewBinding.edu.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004006")) {
                    interest.remove("004006")
                    interest.remove("004006001")
                    interest.remove("004006002")
                    interest.remove("004006003")
                    interest.remove("004006004")
                    interest.remove("004006005")
                    interest.remove("004006006")
                }
                uninterest.add("004006")
                uninterest.add("004006001")
                uninterest.add("004006002")
                uninterest.add("004006003")
                uninterest.add("004006004")
                uninterest.add("004006005")
                uninterest.add("004006006")
            }
        }

        viewBinding.found.setOnClickListener(){
            f++
            if(f % 2 == 1){
                viewBinding.found.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.found.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004002")) {
                    uninterest.remove("004002")
                    uninterest.remove("004002001")
                    uninterest.remove("004002002")
                    uninterest.remove("004002003")
                }
                interest.add("004002")
                interest.add("004002001")
                interest.add("004002002")
                interest.add("004002003")
            }
            else{
                viewBinding.found.setBackgroundResource(R.drawable.btn_style)
                viewBinding.found.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004002")) {
                    interest.remove("004002")
                    interest.remove("004002001")
                    interest.remove("004002002")
                    interest.remove("004002003")
                }
                uninterest.add("004002")
                uninterest.add("004002001")
                uninterest.add("004002002")
                uninterest.add("004002003")
            }
        }

        viewBinding.health.setOnClickListener(){
            h++
            if(h % 2 == 1){
                viewBinding.health.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.health.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004004001")) {
                    uninterest.remove("004004")
                    uninterest.remove("004004001")
                }
                interest.add("004004001")
                interest.add("004004")
            }
            else{
                viewBinding.health.setBackgroundResource(R.drawable.btn_style)
                viewBinding.health.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004004001")) {
                    interest.remove("004004")
                    interest.remove("004004001")
                }
                uninterest.add("004004001")
                uninterest.add("004004")
            }
        }

        viewBinding.etc.setOnClickListener(){
            etc++
            if(etc % 2 == 1){
                viewBinding.etc.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.etc.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004005")) {
                    uninterest.remove("004005")
                    uninterest.remove("004005001")
                    uninterest.remove("004005002")
                    uninterest.remove("004005003")
                }
                interest.add("004005")
                interest.add("004005001")
                interest.add("004005002")
                interest.add("004005003")
            }
            else{
                viewBinding.etc.setBackgroundResource(R.drawable.btn_style)
                viewBinding.etc.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004005")) {
                    interest.remove("004005")
                    interest.remove("004005001")
                    interest.remove("004005002")
                    interest.remove("004005003")
                }
                uninterest.add("004005")
                uninterest.add("004005001")
                uninterest.add("004005002")
                uninterest.add("004005003")
            }
        }

        if(intent.hasExtra("ID") && intent.hasExtra("pw") && intent.hasExtra("Name") && intent.hasExtra("tel")
            && intent.hasExtra("birth") && intent.hasExtra("address") && intent.hasExtra("token")){
            ID4 = intent.getStringExtra("ID").toString()
            pw4 = intent.getStringExtra("pw").toString()
            Name4 = intent.getStringExtra("Name").toString()
            tel4 = intent.getStringExtra("tel").toString()
            birth4 = intent.getStringExtra("birth").toString()
            address4 = intent.getStringExtra("address").toString()
            checkbox_status_sms4 = intent.getStringExtra("checkbox_status_sms").toString()
            checkbox_status_kkt4 = intent.getStringExtra("checkbox_status_kkt").toString()
            checkbox_status_info4 = intent.getStringExtra("checkbox_status_info").toString()
            gettoken = intent.getStringExtra("token").toString()


        }
        else{
            Log.e("받은 값이 없습니다.","받은 값이 없습니다.")
        }

        viewBinding.finish.setOnClickListener{
            Log.e("finish num",num.toString())
            for(i in 0..interest.size-1){
                val v = interest.get(i)
                jsonInterest.put(v)
            }
            for(i in 0..uninterest.size-1){
                val v = uninterest.get(i)
                jsonUnInterest.put(v)
            }
            if(possible == 1){
                Toast.makeText(this@MainActivity_interest,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()

                val userData = Signup2RequestBody(gettoken,tel4, birth4,address4,
                    checkbox_status_info4.toInt(),checkbox_status_sms4.toInt(),checkbox_status_kkt4.toInt(),
                    interest,uninterest
                )

                Log.d("userData","$userData")

                RetrofitWork(userData).work()

                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@MainActivity_interest,"선택된 관심분야가 없습니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}

    class RetrofitWork(private val userInfo: Signup2RequestBody){
        fun work(){
            val service= RetrofitClient.emgMedService

            //        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
            service.Signup23Patch(userInfo)
                .enqueue(object: retrofit2.Callback<Signup2ResponseBody>{
                    override fun onResponse(
                    call: Call<Signup2ResponseBody>,
                    response: Response<Signup2ResponseBody>,
                ) {
                    if(response.isSuccessful){
                        val result = response.body()
                        Log.d("회원가입 성공","$result")
                    }
                }
                    override fun onFailure(call: Call<Signup2ResponseBody>, t: Throwable) {
                        Log.d("회원가입 실패",t.message.toString())

                    }

                })
        }
    }
