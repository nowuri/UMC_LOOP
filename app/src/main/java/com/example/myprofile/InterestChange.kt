package com.example.myprofile

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.interested.R
import com.example.interested.databinding.ActivityInterestChangeBinding
import com.example.network.*
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer
import retrofit2.Call
import retrofit2.Response
import java.sql.Types.NULL
import java.util.logging.Level.parse

class InterestChange : AppCompatActivity() {

    private lateinit var viewBinding : ActivityInterestChangeBinding

    var num = 0
    var l =0; var t=0; var m=0; var w=0; var e=0; var f=0; var h=0; var etc=0
    var possible=-1

    val jsonInterest = JSONArray()
    val jsonUnInterest = JSONArray()
    val interest = ArrayList<String>()
    val uninterest = ArrayList<String>()
    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJuYW1lIjoiam9vbiJ9LCJpYXQiOjE2NzUzNTQ3OTh9.MaPPaQjlXqgDR6P84mO2UNj8Oi6lvtUsljGEJZxbuc8"
    val jsonObject = JSONObject("{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7InVzZXJJZHgiOjEsInVzZXJuYW1lIjoiam9vbiJ9LCJpYXQiOjE2NzUzNTQ3OTh9.MaPPaQjlXqgDR6P84mO2UNj8Oi6lvtUsljGEJZxbuc8\"}")
    val data = token(
        jsonObject.getString("token")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInterestChangeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Log.e("관심분야 수정","관심분야 가져오기 시작")

        uninterest.add("004003002")
        uninterest.add("004004002")
        uninterest.add("004003001")
        uninterest.add("004003003")
        uninterest.add("004001")
        uninterest.add("004006")
        uninterest.add("004002")
        uninterest.add("004004001")
        uninterest.add("004005")
        val service = RetrofitClient.emgMedService

        fun paint(string: String){
            if(string == "004003002"){
                viewBinding.live.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.live.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004003002")
                interest.add("004003002")
            }
            else if(string == "004004002"){
                viewBinding.transport.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.transport.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004004002")
                interest.add("004004002")
            }
            else if(string == "004003001" || string == "004003003"){
                viewBinding.money.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.money.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004003001")
                uninterest.remove("004003003")
                interest.add("004003001")
                interest.add("004003003")
            }
            else if(string == "004001"){
                viewBinding.work.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.work.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004001")
                interest.add("004001")
            }
            else if(string == "004006"){
                viewBinding.edu.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.edu.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004006")
                interest.add("004006")
            }
            else if(string == "004002"){
                viewBinding.found.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.found.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004002")
                interest.add("004002")
            }
            else if(string == "004004001"){
                viewBinding.health.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.health.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004004001")
                interest.add("004004001")
            }
            else{
                viewBinding.etc.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.etc.setTextColor(Color.parseColor("#ffffff"))
                num++
                uninterest.remove("004005")
                interest.add("004005")
            }
        }

        service.getInterest(data)
            .enqueue(object: retrofit2.Callback<changeGetResponseBody>{
                override fun onResponse(
                    call: Call<changeGetResponseBody>,
                    response: Response<changeGetResponseBody>,
                ) {
                    val content = response.body()
                    Log.e("관심분야 가져오기 성공","$content")

                    val jsonObject = content?.result
                    Log.e("jsonObject","$jsonObject")

                    val jsonToString = jsonObject.toString()
                    Log.e("jsonToString","$jsonToString")

                    var getChecked : ArrayList<String> = arrayListOf()
                    var getUnchecked : ArrayList<String> = arrayListOf()
                    var i = 0
                    var code = ""

                    while(i < jsonToString.length){
                        if(jsonToString[i] == ','){
                            getChecked.add(code)
                            code = ""
                        }
                        else if(jsonToString[i] >= '0' && jsonToString[i] <= '9'){
                            code += jsonToString[i]
                        }
                        else if (jsonToString[i] == 'u'){
                            break
                        }
                        i++
                    }

                    var j = i
                    while(j < jsonToString.length){
                        if(jsonToString[j] == ','){
                            getUnchecked.add(code)
                            code = ""
                        }
                        else if(jsonToString[j] >= '0' && jsonToString[j] <= '9'){
                            code += jsonToString[j]
                        }
                        else {

                        }
                        j++
                    }
                    Log.e("getChecked", "$getChecked")

                    var k = 0
                    while(k < getChecked.size){
                        Log.e("paint","paint")
                        paint(getChecked[k])
                        k++
                    }
                }

                override fun onFailure(call: Call<changeGetResponseBody>, t: Throwable) {
                    Log.e("관심분야 가져오기 실패",t.message.toString())
                }

            })



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

        viewBinding.back2.setOnClickListener(){
//            이전 화면으로 돌아갈 수 있도록 함
            val intent2 = Intent(this, MyProfileActivity::class.java)
            startActivity(intent2)
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
                if(uninterest.contains("004003002"))
                    uninterest.remove("004003002")
                interest.add("004003002")
            }
            else{
                viewBinding.live.setBackgroundResource(R.drawable.btn_style)
                viewBinding.live.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004003002")){
                    interest.remove("004003002")
                }
                uninterest.add("004003002")
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
                if(uninterest.contains("004001"))
                    uninterest.remove("004001")
                interest.add("004001")
            }
            else{
                viewBinding.work.setBackgroundResource(R.drawable.btn_style)
                viewBinding.work.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004001"))
                    interest.remove("004001")
                uninterest.add("004001")
            }
        }

        viewBinding.edu.setOnClickListener(){
            e++
            if(e % 2 == 1){
                viewBinding.edu.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.edu.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004006"))
                    uninterest.remove("004006")
                interest.add("004006")
            }
            else{
                viewBinding.edu.setBackgroundResource(R.drawable.btn_style)
                viewBinding.edu.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004006"))
                    interest.remove("004006")
                uninterest.add("004006")
            }
        }

        viewBinding.found.setOnClickListener(){
            f++
            if(f % 2 == 1){
                viewBinding.found.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.found.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004002"))
                    uninterest.remove("004002")
                interest.add("004002")
            }
            else{
                viewBinding.found.setBackgroundResource(R.drawable.btn_style)
                viewBinding.found.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004002"))
                    interest.remove("004002")
                uninterest.add("004002")
            }
        }

        viewBinding.health.setOnClickListener(){
            h++
            if(h % 2 == 1){
                viewBinding.health.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.health.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004004001"))
                    uninterest.remove("004004001")
                interest.add("004004001")
            }
            else{
                viewBinding.health.setBackgroundResource(R.drawable.btn_style)
                viewBinding.health.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004004001"))
                    interest.remove("004004001")
                uninterest.add("004004001")
            }
        }

        viewBinding.etc.setOnClickListener(){
            etc++
            if(etc % 2 == 1){
                viewBinding.etc.setBackgroundResource(R.drawable.changed_btn)
                viewBinding.etc.setTextColor(Color.parseColor("#ffffff"))
                num++
                Log.e("num",num.toString())
                if(uninterest.contains("004005"))
                    uninterest.remove("004005")
                interest.add("004005")
            }
            else{
                viewBinding.etc.setBackgroundResource(R.drawable.btn_style)
                viewBinding.etc.setTextColor(Color.parseColor("#1D2D69"))
                num--
                Log.e("num",num.toString())
                if(interest.contains("004005"))
                    interest.remove("004005")
                interest.add("004005")
            }
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
                val input = interestChangeRequestBody(token,interest,uninterest)
                patch(input).work()

                Log.d("Interest",jsonInterest.toString())
                Log.d("UnInterest",jsonUnInterest.toString())

                val intent = Intent(this, MyProfileActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"선택된 관심분야가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }



    }

    class patch(private val info: interestChangeRequestBody){
        fun work(){
            val service = RetrofitClient.emgMedService

            service.interestChange(info)
                .enqueue(object : retrofit2.Callback<interestChangeResponseBody>{
                    override fun onResponse(
                        call: Call<interestChangeResponseBody>,
                        response: Response<interestChangeResponseBody>,
                    ) {
                        val result = response.body()
                        Log.e("관심분야 변경 성공","$result")
                    }

                    override fun onFailure(call: Call<interestChangeResponseBody>, t: Throwable) {
                        Log.e("관심분야 변경 실패",t.message.toString())
                    }

                })
        }
    }
}