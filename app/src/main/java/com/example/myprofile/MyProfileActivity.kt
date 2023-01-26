package com.example.myprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.interested.databinding.ActivityMypageBinding
import com.example.interested.databinding.ActivityMyprofileBinding

class MyProfileActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityMyprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMyprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //프로필 아이콘 클릭 시 프로필 변경

        //유저 ID 텍스트 뷰에 보여줌

        //회원정보 변경

        //관심분야 변경

        //회원 탈퇴 (카드뷰 띄움)
    }
}