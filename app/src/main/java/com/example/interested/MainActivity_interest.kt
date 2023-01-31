package com.example.interested

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.home.Home
import com.example.interested.databinding.ActivityMainBinding
import com.example.search.Search
import retrofit2.Retrofit
import com.example.network.RetrofitService
import com.example.network.RetrofitClient
import com.example.network.SignUpRequestBody
import com.example.network.SignUpResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


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
    var email4: String = ""

    var checkbox_status_sms4: String = ""
    var checkbox_status_kkt4: String = ""
    var checkbox_status_info4: String = ""


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
            }
            else{
                viewBinding.live.setBackgroundResource(R.drawable.btn_style)
                viewBinding.live.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.transport.setOnClickListener(){
            t++
            if(l % 2 == 1){
                viewBinding.transport.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.transport.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.transport.setBackgroundResource(R.drawable.btn_style)
                viewBinding.transport.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.money.setOnClickListener(){
            m++
            if(m % 2 == 1){
                viewBinding.money.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.money.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.money.setBackgroundResource(R.drawable.btn_style)
                viewBinding.money.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.work.setOnClickListener(){
            w++
            if(w % 2 == 1){
                viewBinding.work.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.work.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.work.setBackgroundResource(R.drawable.btn_style)
                viewBinding.work.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.edu.setOnClickListener(){
            e++
            if(e % 2 == 1){
                viewBinding.edu.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.edu.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.edu.setBackgroundResource(R.drawable.btn_style)
                viewBinding.edu.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.found.setOnClickListener(){
            f++
            if(f % 2 == 1){
                viewBinding.found.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.found.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.found.setBackgroundResource(R.drawable.btn_style)
                viewBinding.found.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.health.setOnClickListener(){
            h++
            if(h % 2 == 1){
                viewBinding.health.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.health.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.health.setBackgroundResource(R.drawable.btn_style)
                viewBinding.health.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        viewBinding.etc.setOnClickListener(){
            etc++
            if(etc % 2 == 1){
                viewBinding.etc.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.etc.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
            }
            else{
                viewBinding.etc.setBackgroundResource(R.drawable.btn_style)
                viewBinding.etc.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
            }
        }

        if(intent.hasExtra("ID") && intent.hasExtra("pw") && intent.hasExtra("Name") && intent.hasExtra("tel")
            && intent.hasExtra("birth") && intent.hasExtra("address") && intent.hasExtra("email")){
            ID4 = intent.getStringExtra("ID").toString()
            pw4 = intent.getStringExtra("pw").toString()
            Name4 = intent.getStringExtra("Name").toString()
            tel4 = intent.getStringExtra("tel").toString()
            birth4 = intent.getStringExtra("birth").toString()
            address4 = intent.getStringExtra("address").toString()
            email4 = intent.getStringExtra("email").toString()
            checkbox_status_sms4 = intent.getStringExtra("checkbox_status_sms").toString()
            checkbox_status_kkt4 = intent.getStringExtra("checkbox_status_kkt").toString()
            checkbox_status_info4 = intent.getStringExtra("checkbox_status_info").toString()

        }
        else{
            Toast.makeText(this,"받은 값이 없습니다.",Toast.LENGTH_SHORT).show()
        }

        viewBinding.finish.setOnClickListener{
            Log.e("finish num",num.toString())
            if(possible == 1){
                Toast.makeText(this@MainActivity_interest,"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                Toast.makeText(this,ID4 + " "+ pw4 + " "+ email4,Toast.LENGTH_SHORT).show()

                val userData = SignUpRequestBody(
                    "ID4"
                )

                val retrofitWork = RetrofitWork(userData)
                retrofitWork.work()

                val intent = Intent(this, Home::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@MainActivity_interest,"선택된 관심분야가 없습니다.",Toast.LENGTH_SHORT).show()
            }
        }

    }

    class RetrofitWork(private val userInfo: SignUpRequestBody) {
        fun work() {
            val service = RetrofitClient.emgMedService

//        Call 작업은 두 가지로 실행됨
//        execute 를 사용하면 request 를 보내고 response 를 받는 행위를 동기적으로 수행한다.
//        enqueue 작업을 실행하면 request 는 비동기적으로 보내고, response 는 콜백으로 받게 된다.
            service.addUserByEnqueue(userInfo)
                .enqueue(object : retrofit2.Callback<SignUpResponseBody> {
                    override fun onResponse(
                        call: Call<SignUpResponseBody>,
                        response: Response<SignUpResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            Log.d("회원가입 성공", "$result")
                        }
                    }

                    override fun onFailure(call: Call<SignUpResponseBody>, t: Throwable) {
                        Log.d("회원가입 실패", t.message.toString())
                    }
                })
        }
    }

}