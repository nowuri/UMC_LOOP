package com.example.description

import com.example.interested.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home.HomeAdapter1
import com.example.home.Homedata
import com.example.interested.SignUp3Activity
import com.example.interested.databinding.ActivityDescriptionBinding
import com.example.network.RetrofitClient
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class Description : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewBinding: ActivityDescriptionBinding
    var recomm_comment: String = ""
    var unrecomm_comment: String = ""
    //var id:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDescriptionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        if(intent.hasExtra("id")){
            val policyId = intent.getStringExtra("id").toString()
            RetrofitWork(policyId).work()
            Log.e("HomeSeoul에서 가져온 id:",policyId)
        }

        var commentView = LayoutInflater.from(this).inflate(R.layout.policy_comment, null, false)

        //TODO: 정책 정보들(이름, 기관, 날짜, 내용, 홈피) 넘어온 데이터로 텍스트 세팅
        //viewBinding.policyName.setText()
        //viewBinding.policyAgency.setText()
        //viewBinding.policyDate.setText()
        //viewBinding.policyDescription.setText()

        //TODO: 홈피는 하이퍼링크로 연결
        //viewBinding.policyHomepage.setText()

        //추천 버튼 클릭
        viewBinding.btnRecomm.setOnClickListener(this)
        //비추천 버튼 클릭
        viewBinding.btnUnrecomm.setOnClickListener(this)

        //후기 보러 가기 클릭
        viewBinding.policyGetReview.setOnClickListener(){
            val intent = Intent(this,Recommend::class.java)
            startActivity(intent)
        }

        //TODO: 지원하기 클릭시 신청 사이트 하이퍼링크로 연결
        //viewBinding.btnApply.setOnClickListener(){
        //    val intent = Intent(this,Recommend::class.java)
        //    startActivity(intent)
        //}

    }


    inner class RetrofitWork(private val policyId: String){
        fun work(){
            Log.e("정책 세부 정보 불러오기","시작")
            val service = RetrofitClient.emgMedService

            service.PolicyDetailGet(policyId)
                .enqueue(object : retrofit2.Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("세부화면| 정책 불러오기 성공","$responsebody")

                            //2.정책 result array 형식으로 뽑아옴
                            val jsonArray = response.body()?.getAsJsonArray("result")
                            Log.e("세부화면| 정책 array:","$jsonArray")

                            //3. 정책 array에서 정책 다섯개 뽑아옴
                            if (jsonArray != null) {
                                var a: Int = 0
                                while(a <= 0){
                                    val Jsonfor = jsonArray[a].getAsJsonObject()
                                    val policyname = Jsonfor.get("policyName").getAsString()
                                    val department = Jsonfor.get("cnsgNmor").getAsString()
                                    val applydate = Jsonfor.get("rqutPrdCn").getAsString()
                                    val url = Jsonfor.get("rqutUrla").getAsString()
                                    val applyprocess = Jsonfor.get("rqutProcCn").getAsString()

                                    val requirements = Jsonfor.get("ageInfo").getAsString()+
                                            Jsonfor.get("empmSttsCn").getAsString()+
                                            Jsonfor.get("accrRqisCn").getAsString()+
                                            Jsonfor.get("majrRqisCn").getAsString()+
                                            Jsonfor.get("splzRlmRqisCn").getAsString()

                                    val introduction = Jsonfor.get("polyItcnCn").getAsString()
                                    val assitdetail = Jsonfor.get("sporCn").getAsString()

                                    viewBinding.name.setText(policyname)
                                    viewBinding.applydate.setText(applydate)
                                    viewBinding.policyUrl.setText(url)
                                    viewBinding.policyProcess.setText(applyprocess)
                                    viewBinding.policyRequirements.setText(requirements)
                                    viewBinding.policyIntroduce.setText(introduction)
                                    viewBinding.policyAssist.setText(assitdetail)
                                    a+=1
                                }
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

    override fun onClick(v: View?) {
        when(v?.id){
            //추천
            viewBinding.btnRecomm.id ->{
                viewBinding.btnRecomm.setBackgroundResource(R.drawable.btn_recomm_selected)
                viewBinding.btnRecomm.setTextColor(Color.WHITE)
                val dlg = CommentDialog(this)
                dlg.show()

                //입력 데이터 전달
                dlg.setOnClickedListener(object : CommentDialog.ButtonClickListener {
                    override fun onClicked(inputComment: String) {
                        recomm_comment = inputComment
                        Log.e("받은 코멘트 값", recomm_comment)
                    }
                })
                val intent = Intent(this, Recommend::class.java)
                intent.putExtra("recomm_comment",recomm_comment)

                viewBinding.btnRecomm.setClickable(false)
                viewBinding.btnUnrecomm.setClickable(false)
            }
            //비추천
            viewBinding.btnUnrecomm.id ->{
                viewBinding.btnUnrecomm.setBackgroundResource(R.drawable.btn_unrecomm_selected)
                viewBinding.btnUnrecomm.setTextColor(Color.WHITE)
                val dlg = CommentDialog(this)
                dlg.show()

                //입력 데이터 전달
                dlg.setOnClickedListener(object : CommentDialog.ButtonClickListener {
                    override fun onClicked(inputComment: String) {
                        unrecomm_comment = inputComment
                        Log.e("받은 코멘트 값", unrecomm_comment)
                    }
                })
                val intent = Intent(this, NotRecommend::class.java)
                intent.putExtra("unrecomm_comment",unrecomm_comment)

                viewBinding.btnRecomm.setClickable(false)
                viewBinding.btnUnrecomm.setClickable(false)
            }
        }
    }
}
