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
import com.example.interested.databinding.ActivityDescriptionBinding
import com.example.network.RetrofitClient
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class Description2 : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewBinding: ActivityDescriptionBinding
    private lateinit var mAdapter: RecyclerAdapter
    var recomm_comment: String = ""
    var unrecomm_comment: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDescriptionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val policyId = intent.getStringExtra("id").toString()
        loadData(policyId)
        Log.e("세부 화면| 가져온 id:", policyId)

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
        viewBinding.policyGetReview.setOnClickListener() {
            val intent = Intent(this, Recommend::class.java)
            startActivity(intent)
        }

        //TODO: 지원하기 클릭시 신청 사이트 하이퍼링크로 연결
        //viewBinding.btnApply.setOnClickListener(){
        //    val intent = Intent(this,Recommend::class.java)
        //    startActivity(intent)
        //}

    }

    private fun setAdapter(list: ArrayList<DescriptionData>) {
        mAdapter = RecyclerAdapter(list, this)
        viewBinding.vpAdapter.adapter = mAdapter
        viewBinding.vpAdapter.layoutManager = LinearLayoutManager(this)
    }

    private fun loadData(policyId: String) {
        Log.e("정책 세부 정보 불러오기", "시작")
        val service = RetrofitClient.emgMedService

        service.PolicyDetailGet(policyId)
            .enqueue(object : retrofit2.Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>,
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()?.toString()

                        val jsonObject = JSONObject(responseBody)
                        val empsInfoObject =
                            jsonObject.getJSONObject("result").getJSONObject("empsInfo")
                        val empArray = empsInfoObject.getJSONArray("emp")

                        if (empArray.length() > 0) {
                            val empObject = empArray.getJSONObject(0)

                            val url = empObject.getString("rqutUrla")
                            val process = empObject.getString("rqutProcCn")
                            val requirements =
                                empObject.getString("ageInfo") +
                                        empObject.getString("empmSttsCn") +
                                        empObject.getString("accrRqisCn") +
                                        empObject.getString("majrRqisCn") +
                                        empObject.getString("splzRlmRqisCn")

                            val assist = empObject.getString("polyItcnCn")
                            val introduce = empObject.getString("polyItcnCn")

                            // polyBizSjnm과 polyItcnCn 값을 사용하여 DescriptionData 객체 생성
                            val descriptionData =
                                DescriptionData(url, process, requirements, introduce, assist)

                            val dataList = ArrayList<DescriptionData>()
                            dataList.add(descriptionData)

                            Log.e("datalist:", dataList.toString())

                            setAdapter(dataList)

                            viewBinding.vpAdapter.layoutManager =
                                LinearLayoutManager(this@Description2)
                            viewBinding.vpAdapter.adapter = mAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("this is error", t.message.toString())
                }
            })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            //추천
            viewBinding.btnRecomm.id -> {
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
                intent.putExtra("recomm_comment", recomm_comment)

                viewBinding.btnRecomm.setClickable(false)
                viewBinding.btnUnrecomm.setClickable(false)
            }
            //비추천
            viewBinding.btnUnrecomm.id -> {
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
                intent.putExtra("unrecomm_comment", unrecomm_comment)

                viewBinding.btnRecomm.setClickable(false)
                viewBinding.btnUnrecomm.setClickable(false)
            }
        }
    }
}
