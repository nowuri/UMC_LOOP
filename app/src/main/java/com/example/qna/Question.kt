package com.example.qna

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.example.interested.R
import com.example.interested.SignUp2Activity
import com.example.interested.databinding.ActivityQuestionBinding
import com.example.search.Search
import com.jakewharton.threetenabp.AndroidThreeTen
import java.time.format.DateTimeFormatter
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat

class Question : AppCompatActivity() {
    private lateinit var viewBinding: ActivityQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        viewBinding = ActivityQuestionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        val currentTime : Long = System.currentTimeMillis()
        val dataFormat = SimpleDateFormat("HH")

        viewBinding.kakao.setOnClickListener(){
            val address = "https://www.youthcenter.go.kr/kakao/kakao.html"

            if(dataFormat.format(currentTime).toInt() >= 8 && dataFormat.format(currentTime).toInt()<=20 ) {
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
            if(dataFormat.format(currentTime).toInt() >= 9 && dataFormat.format(currentTime).toInt()<=18 ) {
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
            Toast.makeText(this,"홈화면으로 이동합니다",Toast.LENGTH_SHORT).show()
        }

        viewBinding.menuSearch.setOnClickListener(){
            val intent = Intent(this,Search::class.java)
            startActivity(intent)
        }
        viewBinding.menuProfile.setOnClickListener(){
            Toast.makeText(this,"프로필로 이동합니다", Toast.LENGTH_SHORT).show()
        }
        viewBinding.login.setOnClickListener(){
            val intent = Intent(this, SignUp2Activity::class.java)
            startActivity(intent)
        }
    }
}