package com.example.description

import com.example.interested.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.ActivityDescriptionBinding
import com.example.myprofile.RemoveUserDialog

class Description : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewBinding: ActivityDescriptionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityDescriptionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

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
        viewBinding.btnUnrecomm.setOnClickListener(){
            val intent = Intent(this,NotRecommend::class.java)
            startActivity(intent)
        }

        //후기 보러 가기 클릭
        viewBinding.policyGetReview.setOnClickListener(){
            val intent = Intent(this,Recommend::class.java)
            startActivity(intent)
        }

        //회원 탈퇴 클릭 시 카드뷰 띄움

        //TODO: 지원하기 클릭시 신청 사이트 하이퍼링크로 연결
        //viewBinding.btnApply.setOnClickListener(){
        //    val intent = Intent(this,Recommend::class.java)
        //    startActivity(intent)
        //}
    }

    override fun onClick(v: View?) {
        when(v?.id){
            viewBinding.btnRecomm.id ->{
                val dlg = CommentDialog(this)
                dlg.show()
            }
        }
    }

    //private fun createView(v: View) {

     //   val recommend: EditText = EditText(applicationContext)
     //   recommend.setHint("이유를 작성해주세요")
      //  recommend

    //}
}
