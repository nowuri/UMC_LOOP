package com.example.qna

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.interested.R
import com.example.interested.databinding.ActivityQuestionBinding
import com.example.search.Search

class Question : AppCompatActivity() {
    private lateinit var viewBinding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.kakao.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/kakao/kakao.html"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }

        viewBinding.operator.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/deepConslt/deepConsltIndex.do"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }

        viewBinding.call.setOnClickListener(){
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:18119876")
            if(intent.resolveActivity(packageManager) != null){
                startActivity(intent)
            }
        }

        viewBinding.board.setOnClickListener() {
            val address = "https://www.youthcenter.go.kr/jobConslt/jobConsltList.do#login_chk"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }

        viewBinding.menuHome.setOnClickListener(){
            Toast.makeText(this,"홈화면으로 이동합니다",Toast.LENGTH_SHORT).show()
        }

        viewBinding.menuSearch.setOnClickListener(){
            val intent = Intent(this,Search::class.java)
            startActivity(intent)
        }
        viewBinding.menuProfile.setOnClickListener(){
            Toast.makeText(this,"프로필로 이동합니다", Toast.LENGTH_SHORT).show()
        }
    }
}