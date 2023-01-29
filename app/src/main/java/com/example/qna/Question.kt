package com.example.qna

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home.Home
import com.example.interested.databinding.ActivityQuestionBinding
import com.example.login.Login
import com.example.mypage.MyPage_MainActivity
import com.example.search.Search
import java.text.SimpleDateFormat
import java.util.*

class Question : AppCompatActivity() {
    private lateinit var viewBinding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val currentTime : Long = System.currentTimeMillis()
        val dataFormat = SimpleDateFormat("HH")
        val cal = Calendar.getInstance()
        val nWeek = cal.get(Calendar.DAY_OF_WEEK)

        viewBinding.kakao.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/kakao/kakao.html"

            if(dataFormat.format(currentTime).toInt() >= 8 && dataFormat.format(currentTime).toInt()<=20
                && nWeek != 7 && nWeek != 1) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
                startActivity(intent)
            }
            else{
                val dlg = Dialog1(this)
                dlg.myDig()
            }
        }

        viewBinding.operator.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/deepConslt/deepConsltIndex.do"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }

        viewBinding.call.setOnClickListener(){
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:18119876")
            if(dataFormat.format(currentTime).toInt() >= 9 && dataFormat.format(currentTime).toInt()<=18
                && nWeek != 7 && nWeek != 1) {
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
            else{
                val dlg = Dialog1(this)
                dlg.myDig()
            }
        }

        viewBinding.board.setOnClickListener() {
            val address = "https://www.youthcenter.go.kr/jobConslt/jobConsltList.do#login_chk"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            startActivity(intent)
        }

        viewBinding.menuHome.setOnClickListener(){
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }

        viewBinding.menuSearch.setOnClickListener(){
            val intent = Intent(this,Search::class.java)
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
    }
}