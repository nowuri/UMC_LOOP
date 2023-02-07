package com.example.description

import com.example.interested.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.interested.databinding.ActivityDescriptionBinding

class Description : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewBinding: ActivityDescriptionBinding
    var recomm_comment: String = ""
    var unrecomm_comment: String = ""


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
                viewBinding.btnRecomm.setClickable(false)
                viewBinding.btnUnrecomm.setClickable(false)
            }
        }
    }
}
