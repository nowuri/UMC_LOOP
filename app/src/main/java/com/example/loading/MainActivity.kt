package com.example.loading

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.home.Home
import com.example.interested.SignUp3Activity
import com.example.interested.databinding.ActivityMainLoadingBinding
import com.example.search.Search
//타 화면 테스트용으로 임시 버튼 추가하겠습니다!
import com.example.interested.SignUp2Activity
import com.example.mypage.MyPage_MainActivity
import com.example.myprofile.MyProfileActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMainLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainLoadingBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Handler().postDelayed({ startActivity(Intent(this, Home::class.java)) }, 2000L)

        //타 화면 테스트용 버튼 (삭제예정)
        viewBinding.button.setOnClickListener{
            val intent = Intent(this, MyProfileActivity::class.java)
            startActivity(intent)
        }
    }
}